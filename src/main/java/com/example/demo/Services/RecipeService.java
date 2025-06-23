package com.example.demo.Services;
import java.util.List;


import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;
import com.example.demo.repository.ImgRepository;
import com.example.demo.repository.StepRepository;
import com.example.demo.Step;
import com.example.demo.DTO.RecipeRequest;
import com.example.demo.Img;
import java.util.Map;
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
		mainImg.setPath(recipe_request.getMainImg());//DTOからデータをゲットしそれをエンティティクラスのsetterでセット
		img_repository.saveAndFlush(mainImg);
		//DTOからRecipeオブジェクトをもらってエンティティに保存
		//レシピを保存するとID が自動生成（@GeneratedValue）される
		Recipe recipe = new Recipe();
		recipe.setName(recipe_request.getName()); 
		recipe.setComment(recipe_request.getComment());
		recipe.setMainImg(mainImg);
		repository.saveAndFlush(recipe);//recipe_id を発番（@GeneratedValue により）し、この後 Step で外部キーに使われる
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
		    			 img_repository.saveAndFlush(img);
		    		 }    		 
		    		 }
		    	 step.setImg(img); 
		    }
		    step_repository.saveAndFlush(step);
		    
		    }

		    
		

}
@Transactional
public void EditFromForm(RecipeRequest recipe_request) {
	//Img編集
	
}

//特定のリソース表示
}