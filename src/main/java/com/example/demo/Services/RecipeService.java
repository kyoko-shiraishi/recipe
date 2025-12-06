package com.example.demo.Services;
import java.util.*;
import java.lang.Long;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import com.example.demo.repository.*;
import com.example.demo.DTO.*;
import com.example.demo.Services.*;
import com.example.demo.Entities.*;
import java.util.stream.*;
import org.springframework.web.multipart.MultipartFile;



@Service
public class RecipeService {
private final RecipeRepository repository;
private final ImgRepository img_repository;
private final StepRepository step_repository;
private final IngredientRepository ingredient_repository;
private final CateRepository cate_repository;
private final Recipe_IngredietRepository recipe_ingredient_repository;
private final SynonymService syno_service;
private final SaveImgService saved_img_service;
private final NewRecipeService new_recipe_service;
private final BuildStepsService build_steps_service;
public RecipeService(RecipeRepository repository,ImgRepository img_repository,
		StepRepository step_repository,IngredientRepository mate_repository,CateRepository cate_repository,
		Recipe_IngredietRepository amo_repository,SynonymService syno_service,SaveImgService saved_img_service,
		NewRecipeService new_recipe_service,BuildStepsService build_steps_service) {
	this.repository = repository;
	this.img_repository = img_repository;
	this.step_repository = step_repository;
	this.ingredient_repository=mate_repository;
	this.cate_repository=cate_repository;
	this.recipe_ingredient_repository = amo_repository;
	this.syno_service = syno_service;
	this.saved_img_service=saved_img_service;
	this.new_recipe_service = new_recipe_service;
	this.build_steps_service = build_steps_service;
}


//レシピエンティティをIDから取得
public Optional<Recipe> findById(Long id) {
	
	return repository.findById(id);
}

//一意のレシピの手順リストを取得
public List<Step> findByRecipeId(Long recipe_id) {
	return step_repository.findByRecipeId(recipe_id);
}

//レシピをIDから削除
@Transactional
public void deleteById(Long id) {
	repository.deleteById(id);
}

//saveでレシピエンティティを保存し、即座にDBへ反映
@Transactional
public void saveAndFlush(Recipe recipe) {
	repository.saveAndFlush(recipe);
}

//レシピエンティティ全て取得
@Transactional
public List<Recipe>  findAll() {
	return repository.findAll();
}

//文字列からその文字列を含むレシピ名をもつレシピエンティティリストを取得
public List<Recipe> findByNameContaining(String keyword){
	return repository.findByNameContaining(keyword);
}


@Transactional
public void createFromForm(RecipeDTO recipeDTO) throws IOException {
// ====== メイン画像およびRecipe 作成 ======
	MultipartFile mainImgFile = recipeDTO.getMainImg();
	Img mainImgEntity = saved_img_service.saveAndRegister(mainImgFile);
	//組み立てはnew_recipe_serviceの責務
	Recipe new_recipe = new_recipe_service.createRecipe(recipeDTO, mainImgEntity);
	//保存は Repository の責務
	Recipe recipe = repository.save(new_recipe);
//====== 手順の保存 ======

    int stepNum = 1;
    List<RecipeDTO.StepDTO> list = recipeDTO.getSteps();
    List<Step> new_steps = new ArrayList<>();
    for(int i=0;i<list.size();i++) {
    	RecipeDTO.StepDTO step_dto = list.get(i);
    	MultipartFile mediaFile = step_dto.getMediaFile();
    	Img step_img =saved_img_service.saveAndRegister(mediaFile);
    	 Step new_step = build_steps_service.buildSteps(step_dto, step_img, recipe, stepNum);
    	new_steps.add(new_step);
    	stepNum++;
    }
    step_repository.saveAll(new_steps);
	    	
	    	    

// ====== 材料の保存 ======
    List<RecipeDTO.Recipe_IngredientDTO> ingredients = recipeDTO.getIngredients();
    List<String> tempList = new ArrayList<>();
    //IngredientDTOリストを回していく
    List<Recipe_Ingredient> recipe_ingredientList = ingredients.stream().map(in -> {
        Recipe_Ingredient newRecipe_Ingredient = new Recipe_Ingredient ();
        //banana（正規化できるなら正規化にしてから検索）がにあるか
        String rawName = in.getName();
        String name = syno_service.normalize(rawName);
        if (optionalIngredient.isPresent()) {
        	newRecipe_Ingredient.setIngredient(optionalIngredient.get());
        } else {
        	//新規材料は仮材料としてあたらしく材料オブジェクトを作る
            Ingredient tempIng = new Ingredient();
            tempIng.setTempMate(true);
            tempIng.setName(in.getName());
            Ingredient savedTempMate = ingredient_repository.save(tempIng);
            newRecipe_Ingredient.setIngredient(savedTempMate);
            tempList.add(savedTempMate.getName());
        }

        newRecipe_Ingredient.setRecipe(recipe);
        newRecipe_Ingredient.setQuantity(in.getQuantity());
        return newRecipe_Ingredient;
    }).collect(Collectors.toList());

    recipe_ingredient_repository.saveAll(recipe_ingredientList);

    // 管理者に通知
    for (String i : tempList) {
        System.out.println("新規材料: " + i);
    }
    }


//レシピ詳細閲覧用。そのレシピの材料と量のオブジェクトを返す
public List<Recipe_Ingredient> findByRecipe(Long id) {
	List<Recipe_Ingredient> recipe_ingredient = recipe_ingredient_repository.findByRecipeId(id);
	return  recipe_ingredient;
}

@Transactional
//データをDTOに詰め替え
public RecipeDTO convertToDto(Recipe recipe, List<Step> steps, List<Recipe_Ingredient> recipe_ingredients) {
    RecipeDTO dto = new RecipeDTO();
    dto.setId(recipe.getId());
    dto.setName(recipe.getName());
    dto.setComment(recipe.getComment());
    // メイン画像パスを保持
    if (recipe.getMainImg() != null) {
       dto.setExistingMainImgId(recipe.getMainImg().getId());
       dto.setExistingMainImgPath(recipe.getMainImg().getPath());
    }
    dto.setMainImg(null);

    // Step DTO
    List<RecipeDTO.StepDTO> stepDTOs = new ArrayList<>();
    for (Step step : steps) {
        RecipeDTO.StepDTO s = new RecipeDTO.StepDTO();
        s.setId(step.getId());
        s.setStepNumber(step.getStepNumber());
        s.setContent(step.getContent());
        if (step.getImg() != null) {
        	//既存画像があるならそのIDとパスを持たせておく
            s.setExistingImgId(step.getImg().getId()); 
            s.setExistingImgPath(step.getImg().getPath());
        }
        stepDTOs.add(s);
    }
    dto.setSteps(stepDTOs);

    // Amount DTO
    List<RecipeDTO.Recipe_IngredientDTO> recipe_ingredientDTOs = new ArrayList<>();
    for (Recipe_Ingredient recipe_ingredient : recipe_ingredients) {
        RecipeDTO.Recipe_IngredientDTO a = new RecipeDTO.Recipe_IngredientDTO();
        a.setId(recipe_ingredient.getId());
        a.setName(recipe_ingredient.getIngredient().getName());
        a.setRecipeId(recipe.getId());
        a.setQuantity(recipe_ingredient.getQuantity());
        recipe_ingredientDTOs.add(a);
    }
    dto.setIngredients(recipe_ingredientDTOs);

    return dto;
}

@Transactional
public void editFromForm(RecipeDTO dto) throws IOException {
// メイン画像を準備(saved_main_imgとして持っておく)
    MultipartFile mainImgFile = dto.getMainImg();
    Long existingMainImgId = dto.getExistingMainImgId();
	  //メイン画像（新規）だった場合
    Img  saved_mainImg = saved_img_service.saveAndRegister(mainImgFile);
	  //メイン画像(既存)だった場合
	    if(saved_mainImg==null&&existingMainImgId != null) {					
	    	saved_mainImg = img_repository.findById(existingMainImgId).orElse(null);
	    }

// レシピ更新
	//idからRecipeオブジェクトを取得
    Recipe recipe = repository.findById(dto.getId()).orElseThrow();
    
    //Recipeオブジェクト「name」のセット
    recipe.setName(dto.getName());
    
    //Recipeオブジェクト「comment」のセット
    recipe.setComment(dto.getComment());
    
    //Recipeオブジェクト「mainImg」のセット(さっき準備したやつを入れる)
     recipe.setMainImg(saved_mainImg);
    
    //Recipeオブジェクト保存
    Recipe savedRecipe = repository.save(recipe);

// ステップ更新
    Step savedStep = new Step();
    
    //使わない(DTOから指定されなかった)ステップはDBから消したい
    //→既存マップでふりおとし！
	//キー：値＝ID：Stepオブジェクト
	    Map<Long, Step> oldStepMap = step_repository.findByRecipeId(savedRecipe.getId())
	            .stream().collect(Collectors.toMap(Step::getId, s -> s));
	    
	 //手順番号インデックスの初期化
	    int stepIndex = 1;
	    
	//SteoDTOリストを回して各StepDTOを見ていく
	    for (RecipeDTO.StepDTO stepDto : dto.getSteps()) {
	     Step step;
	        
   //DTOで使われてて既存マップにもある＝既存ステップの使いまわしだった場合
        if (stepDto.getId() != null && oldStepMap.containsKey(stepDto.getId())) {
            step = oldStepMap.remove(stepDto.getId());
            
    //新規のステップだった場合
        } else {
            step = new Step();
            step.setRecipe(savedRecipe);
        }
      //画像処理
        MultipartFile mediaFile = stepDto.getMediaFile();
	   //画像（削除）だった場合
        if (stepDto.isRemoveImg()) {
            Img oldImg = step.getImg();
            if (oldImg != null) {
                step.setImg(null);
                img_repository.delete(oldImg);  // ←ここでDBから削除
         }
		            
	   //画像（使う）だった場合
        }else{
        	Img img = saved_img_service.saveAndRegister(mainImgFile);     
        	if(img==null&&stepDto.getExistingImgId() != null) {
        		img  = img_repository.findById(stepDto.getExistingImgId()).orElse(null);
        	}
            step.setImg(img);
        }      
        step.setStepNumber(stepIndex++);
        step.setContent(stepDto.getContent());  
    //Stepオブジェクトを保存
    step_repository.save(step);
    }


//材料   
    //既存マップ作成
    Map<Long, Recipe_Ingredient> oldAmountMap = recipe_ingredient_repository.findByRecipeId(savedRecipe.getId())
            .stream().collect(Collectors.toMap(Recipe_Ingredient::getId, a -> a));
    List<String> tempList = new ArrayList<>();
    
    //DTOリストを回して各DTOを見ていく
    for (RecipeDTO.Recipe_IngredientDTO recipe_ingredientDTO : dto.getIngredients()) {
        Recipe_Ingredient newRecipe_Ingredient;
    //既存オブジェクト使いまわし
        if (recipe_ingredientDTO.getId() != null && oldAmountMap.containsKey(recipe_ingredientDTO.getId())) {
        	newRecipe_Ingredient = oldAmountMap.remove(recipe_ingredientDTO.getId());
        }
    //新規材料オブジェクト
        else 
        {
        	newRecipe_Ingredient = new Recipe_Ingredient();
        	newRecipe_Ingredient.setRecipe(recipe);
        }
 
        //シノニムチェック
        Ingredient ing;
        String ingName = syno_service.normalize(recipe_ingredientDTO.getName());
        Optional< Ingredient> OptionalMate = ingredient_repository.findByName(ingName);
                if(OptionalMate.isPresent()) {
                   ing = OptionalMate.get();
                }else{
                	Ingredient tempMate = new Ingredient();//仮フラグを立てたうえでのnew Ingredient
                    tempMate.setTempMate(true);
                    tempMate.setName(recipe_ingredientDTO.getName());
                    tempList.add(recipe_ingredientDTO.getName());
                   ing =  ingredient_repository.save(tempMate);
                }
        newRecipe_Ingredient.setIngredient(ing);
        newRecipe_Ingredient.setQuantity(recipe_ingredientDTO.getQuantity());

        recipe_ingredient_repository.save(newRecipe_Ingredient);
    }

    // 削除された材料・ステップ
    oldStepMap.values().forEach(step_repository::delete);
    oldAmountMap.values().forEach(recipe_ingredient_repository::delete);

    // 管理者通知
    tempList.forEach(System.out::println);

}
	//本登録のみの材料リスト
	public List<Ingredient> findAllIngs(){
		List<Ingredient> data = ingredient_repository.findAll();
		return data;
	}
	//仮登録のみの材料リスト
	public List<Ingredient> showTempMates(){
		List<Ingredient> tempDate = ingredient_repository.findAll().stream()
				.filter(mate->mate.isTempMate()==true)
				.collect(Collectors.toList());
		return tempDate;
	}
	//引数に名前をもらい、mate_repositoryに既存でなければ挿入
	@Transactional
	public String addMate(IngredientDTO dto) {
		Optional<Ingredient> is_existing = ingredient_repository.findByName(dto.getName());
		if(is_existing.isEmpty()) {//データベースにまだない材料だった場合 → 新規登録
			Ingredient new_name = new Ingredient();
			new_name.setName(dto.getName());
			new_name.setCategory(dto.getCategory());
			new_name.setTempMate(false);
			ingredient_repository.save(new_name);
			return "success";//追加成功
		}
		//すでにある場合
		Ingredient existing = is_existing.get();
		// 仮登録だった材料 → 本登録に更新
		if(existing.isTempMate()) {
			existing.setName(dto.getName());
			existing.setCategory(dto.getCategory());
			existing.setTempMate(false);
			ingredient_repository.save(existing);
			return "success";
		}else {
			return "already_exist";
		}
		
	}
	public List<Category> showCateNames(){
		List<Category> data = cate_repository.findAll();
		return data;
	}
	public String addCate(String name) {
		String result;
		Optional<Category> existing = cate_repository.findByname(name);
		if(existing.isEmpty()) {
			Category new_category = new Category();
			new_category.setName(name);
			cate_repository.save(new_category);
			return "success";
		}else {
			return "already_exists";
		}
	}
	
}
	
	
	
	

