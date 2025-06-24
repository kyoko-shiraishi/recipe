package com.example.demo.Services;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.*;
import com.example.demo.repository.*;
import com.example.demo.DTO.*;
import com.example.demo.Step;
import java.util.Map;
import java.util.stream.*;
import java.util.HashMap;
@Service
public class RecipeService {
private final CookingRepository repository;
private final ImgRepository img_repository;
private final StepRepository step_repository;
//コンストラクタインジェクションでサービスクラスからリポジトリ（データベース）を使えるようにする
public RecipeService(CookingRepository repository,ImgRepository img_repository,StepRepository step_repository) {
	this.repository = repository;
	this.img_repository = img_repository;
	this.step_repository = step_repository;
}
//コントローラーなどで使いたいメソッドを定義する
public Optional<Recipe> findById(long id) {
	//リポジトリの既存メソッドを引数渡して呼び出す
	return repository.findById(id);
}
public List<Step> findByRecipeId(long recipe_id) {
	return step_repository.findByRecipeId(recipe_id);
}
@Transactional
public void deleteById(long id) {
	repository.deleteById(id);
}
@Transactional
public void saveAndFlush(Recipe recipe) {
	repository.saveAndFlush(recipe);
}
@Transactional
public List<Recipe>  findAll() {
	return repository.findAll();
}
public List<Recipe> findByNameContaining(String keyword){
	return repository.findByNameContaining(keyword);
}

@Transactional
public void createFromForm(RecipeRequest recipe_request) {
		
		//Imgレコード作成保存
		Img mainImg = new Img();
		mainImg.setPath(recipe_request.getMainImg());//DTOから文字列パスをゲットしそれをエンティティクラスのsetterでセット
		img_repository.save(mainImg);
		//DTOからRecipeオブジェクトをもらってエンティティに保存
		//レシピを保存するとID が自動生成（@GeneratedValue）される
		Recipe recipe = new Recipe();
		recipe.setName(recipe_request.getName()); 
		recipe.setComment(recipe_request.getComment());
		recipe.setMainImg(mainImg);
		repository.save(recipe);//recipe_id を発番（@GeneratedValue により）し、この後 Step で外部キーに使われる
		//DTOから手順リストと手順につかう画像リストをget
		List<String> description = recipe_request.getStepDescription();
		List<String> imges = recipe_request.getStepImg();
		//リストの要素をひとつずつレコードにget,set,save!
		int stepNum = 1;
		Map<String,Img>ImgCache = new HashMap<>();
		
		for (int i = 0; i < description.size(); i++) {
		    String desc = description.get(i);
		    if (desc == null || desc.trim().isEmpty()) continue; // 処理をスキップして次のiに進む

		    Step step = new Step();
		    step.setStepNumber(stepNum++); 
		    step.setContent(desc);
		    step.setRecipe(recipe);
		    
		    // 画像の処理
		    String path = imges.get(i);
		    Img img;
		    if(path !=null&&!path.trim().isEmpty()) {
		    	 img = ImgCache.get(path);
		    	 if(img==null) {
		    		 img = img_repository.findByPath(path).orElse(null);
		    		 if(img==null) {
		    			 img = new Img();
		    			 img.setPath(path);
		    			 img_repository.save(img);
		    		 }    		 
		    		 }
		    	 step.setImg(img); 
		    }
		    step_repository.save(step);
		    
		    }

		    
		

}
@Transactional
//データベースから取得した Recipe（レシピ情報）と Step（手順のリスト）を、
//ーム送信用の RecipeRequest DTO に変換するためのメソッド
public RecipeRequest convertToDto(Recipe recipe,List<Step> steps) {
	RecipeRequest dto = new RecipeRequest();//ここにレシピ名・コメント・手順など、すべての編集対象データを詰め込んでいく
	dto.setId(recipe.getId());
	dto.setName(recipe.getName());
	dto.setComment(recipe.getComment());
	dto.setMainImg(recipe.getMainImg()!=null?recipe.getMainImg().getPath():null);
	//手リストと画像パスリストをそれぞれRecipeRequestに渡す
	List<StepRequest> stepDTOs = new ArrayList<>();
	for(Step step:steps) {
		StepRequest s = new StepRequest();
		s.setId(step.getId());
		s.setStepNumber(step.getStepNumber());
		s.setContent(step.getContent());
		if(step.getImg()!=null) {
			s.setImg(step.getImg().getPath());
		}
		stepDTOs.add(s);
	}
	dto.setSteps(stepDTOs);
	return dto;
}
@Transactional
public void editFromForm(RecipeRequest dto) {
	String path = dto.getMainImg();
	Img img;
	if(img_repository.findByPath(path).isEmpty()) {
		img = new Img();
		img.setPath(path);
		
		img_repository.save(img);
	}else {
		img = img_repository.findByPath(path).get();
	}
	Recipe recipe = repository.findById(dto.getId()).get();
	
	recipe.setName(dto.getName());
	recipe.setComment(dto.getComment());
	recipe.setMainImg(img);
	repository.save(recipe);
	List<StepRequest> steplist =dto.getSteps();
	//steplistの中にdtoからのステップレコード一覧が入ってる。IDは全図同じ（新）
	//同じIDの既存のステップ一覧を用意
	Long id =recipe.getId();
	List<Step> old_steps = step_repository.findByRecipeId(id);
	//新旧比較していく
	//旧ステップリストをマップ化
	
	Map<Long,Step> oldStepMap = old_steps.stream()
			.filter(s->s.getId() != null)
			.collect(Collectors.toMap(Step::getId, s -> s));
	List<Step> updatedSteps = new ArrayList<>();//DTOに基づいて更新または新規作成されたステップ）
	
	for(StepRequest stepDto:steplist) {
		Step step;
		if(stepDto.getId() !=null&&oldStepMap.containsKey(stepDto.getId())) {
			step = oldStepMap.get(stepDto.getId());
			oldStepMap.remove(stepDto.getId());//DTOにない + DBにある＝ユーザーが削除したステップ
		}else {
			step = new Step();
			step.setRecipe(recipe);
		}
		step.setStepNumber(stepDto.getStepNumber());
		step.setContent(stepDto.getContent());
		
		//Img更新
		Img setImg;
		//既存の Step に紐づいた画像があれば取得
		String stepImgpath = stepDto.getImg();
		//DTOからのIMG→指定されているやつが既にDB上にあれば再利用、なければnewしてセット→セットしてステップ保存
		if(stepImgpath != null&&!stepImgpath.isBlank()) {
		if(img_repository.findByPath(stepImgpath).isPresent()) {
			 setImg = img_repository.findByPath(stepImgpath).get();		
		}else {
			 setImg = new Img();
			setImg.setPath(stepImgpath);
			img_repository.save(setImg);
		}
		step.setImg(setImg);//Stepに手順画像をセット
		
		}
		updatedSteps.add(step);
		
	}
	for(Step toDelete: oldStepMap.values()) {//既存のStep
		step_repository.delete(toDelete);;
	}
	for(Step new_step:updatedSteps) {
		step_repository.save(new_step);
	}
		}
			
		}
	
	
	
	

