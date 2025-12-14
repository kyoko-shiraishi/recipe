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
private final IngredientLookup ingredient_lookup;
private final Recipe_IngredientFactory recipe_ingredient_factory;
private final  NewOrOldImg new_old_img;
public RecipeService(RecipeRepository repository,ImgRepository img_repository,
		StepRepository step_repository,IngredientRepository mate_repository,CateRepository cate_repository,
		Recipe_IngredietRepository amo_repository,SynonymService syno_service,SaveImgService saved_img_service,
		NewRecipeService new_recipe_service,BuildStepsService build_steps_service,IngredientLookup ingredient_lookup,
		Recipe_IngredientFactory recipe_ingredient_factory,NewOrOldImg new_old_img) {
	this.repository = repository;
	this.img_repository = img_repository;
	this.step_repository = step_repository;
	this.ingredient_repository=mate_repository;
	this.cate_repository=cate_repository;
	this.recipe_ingredient_repository = amo_repository;
	this.syno_service = syno_service;
	this.saved_img_service=saved_img_service;
	this.ingredient_lookup= ingredient_lookup;
	this.recipe_ingredient_factory = recipe_ingredient_factory;
	this.new_old_img = new_old_img;
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
	Recipe recipe = new Recipe();
	NewRecipeService.of(recipeDTO,recipe , mainImgEntity);
	Recipe new_recipe = repository.save(recipe);
//====== 手順の保存 ======
    int stepNum = 1;
    List<RecipeDTO.StepDTO> list = recipeDTO.getSteps();
    List<Step> new_steps = new ArrayList<>();
    for(int i=0;i<list.size();i++) {
    	RecipeDTO.StepDTO step_dto = list.get(i);
    	MultipartFile mediaFile = step_dto.getMediaFile();
    	Img step_img =saved_img_service.saveAndRegister(mediaFile);
    	 Step new_step = BuildStepsService.of(step_dto, step_img, new_recipe, stepNum);
    	new_steps.add(new_step);
    	stepNum++;
    }
    step_repository.saveAll(new_steps);
// ====== 材料の保存 ======
    List<RecipeDTO.Recipe_IngredientDTO> ingredients = recipeDTO.getIngredients();
    List<Recipe_Ingredient> recipe_ingredient_list = new ArrayList<>();
    //IngredientDTOリストを回していく
    for(int i=0;i<ingredients.size();i++) {
    	RecipeDTO.Recipe_IngredientDTO ingdto = ingredients.get(i);
    	//DTOから材料名取得
        String rawName = ingdto.getName();
        //材料名正規化した結果を格納
        NormalizeResult res = syno_service.normalize(rawName);
        //材料名から既存材料を取得または新規材料を作る
        Ingredient ing = ingredient_lookup.findOrCreateIng(res);
        //材料と記入名、量をセット
        Recipe_Ingredient ri = recipe_ingredient_factory.build_recipe_ingredient(new_recipe, ing, rawName, ingdto);
        recipe_ingredient_list.add(ri);
    }
    recipe_ingredient_repository.saveAll(recipe_ingredient_list);

}

//レシピ詳細閲覧用。そのレシピの材料と量のオブジェクトを返す
public List<Recipe_Ingredient> findByRecipe(Long id) {
	List<Recipe_Ingredient> recipe_ingredient = recipe_ingredient_repository.findByRecipeId(id);
	return  recipe_ingredient;
}


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
    Img mainImg = new_old_img.createImg(mainImgFile, existingMainImgId);
// レシピ更新
	//idからRecipeオブジェクトを取得
    Recipe recipe = repository.findById(dto.getId()).orElseThrow();
    NewRecipeService.of(dto, recipe,mainImg);
    Recipe savedRecipe = repository.save(recipe);

// ステップ更新
    Step savedStep = new Step();
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
	   //画像（削除）だった場合
        if (stepDto.isRemoveImg()) {
            Img oldImg = step.getImg();
            if (oldImg != null) {
                step.setImg(null);
                img_repository.delete(oldImg);  // ←ここでDBから削除
         }
		            
	   //画像（使う）だった場合
        }else{
            MultipartFile mediaFile = stepDto.getMediaFile();
            Long existing = stepDto.getExistingImgId();
            Img step_img = new_old_img.createImg(mediaFile,existing);
            step.setImg(step_img);
        }
        step.setStepNumber(stepIndex++);
        step.setContent(stepDto.getContent());  
    //Stepオブジェクトを保存
    step_repository.save(step);
    }


//材料   
    //既存材料マップ作成
    Map<Long, Recipe_Ingredient> oldAmountMap = recipe_ingredient_repository.findByRecipeId(savedRecipe.getId())
            .stream().collect(Collectors.toMap(Recipe_Ingredient::getId, a -> a));

    
    //DTOリストを回して各DTOを見ていく
    for (RecipeDTO.Recipe_IngredientDTO recipe_ingredientDTO : dto.getIngredients()) {
    	Recipe_Ingredient newRecipe_Ingredient;
    	if (recipe_ingredientDTO.getId() != null && oldAmountMap.containsKey(recipe_ingredientDTO.getId())) {
    	    newRecipe_Ingredient = oldAmountMap.remove(recipe_ingredientDTO.getId()); // 既存
    	} else {
    	    String rawName = recipe_ingredientDTO.getName();
    	    NormalizeResult res = syno_service.normalize(rawName);
    	    Ingredient ing = ingredient_lookup.findOrCreateIng(res);
    	    newRecipe_Ingredient = recipe_ingredient_factory.build_recipe_ingredient(savedRecipe, ing, rawName, recipe_ingredientDTO);
    	}
    	recipe_ingredient_repository.save(newRecipe_Ingredient);

    }
    

    // 削除された材料・ステップ
    oldStepMap.values().forEach(step_repository::delete);
    oldAmountMap.values().forEach(recipe_ingredient_repository::delete);



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
	
	
	
	

