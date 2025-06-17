package com.example.demo.Services;
import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;
import com.example.demo.repository.ImgRepository;
import com.example.demo.repository.StepRepository;
import com.example.demo.RecipeRequest;
import com.example.demo.Step;
import com.example.demo.Img;
import java.util.Map;
import java.util.HashMap;
@Service
public class RecipeService {
private final CookingRepository repository;
private final ImgRepository img_repository;
private final StepRepository step_repository;
public RecipeService(CookingRepository repository,ImgRepository img_repository,StepRepository step_repository) {
	this.repository = repository;
	this.img_repository = img_repository;
	this.step_repository = step_repository;
}
public Optional<Recipe> findById(long id) {
	//リポジトリの既存メソッドを引数渡して呼び出す
	return repository.findById(id);
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
	//DTOからRecipeオブジェクトをもらってエンティティに保存
	//レシピを保存するとID が自動生成（@GeneratedValue）される
	Recipe recipe = new Recipe();
	recipe.setName(recipe_request.getName()); 
	recipe.setComment(recipe_request.getcomment());
	//保存（レシピテーブルのレコード追加完了）
	repository.saveAndFlush(recipe);
	
	//DTOから手順リストと画像リストをもらう
	List<String> description = recipe_request.getStepDescription();
	List<String> imges = recipe_request.getStepImg();
	Map<String,Img> imgMap = new HashMap<>();
	for (int i = 0; i < description.size(); i++) {
	    Step step = new Step();
	    step.setStepNumber(i);
	    step.setContent(description.get(i));
	    step.setRecipe(recipe);

	    //Imgの保存
	    //変数pathにリストからの値を格納
	    //そもそも画像リストが null か確認
	    //i 番目の要素にアクセスできるか確認
	    //その要素が null じゃないか確認
	    if(imges != null && imges.size() > i && imges.get(i) != null) {
	    String path = imges.get(i);
	    
	    //Imgをグローバル変数で宣言しておき、マップに既に画像があるかないかで場合分け
	    if(path !=null) {
	    Img img;
	    if(imgMap.containsKey(path)) {
    	//マップにリストからのパスがあればそれをimgに格納してimgを保存
    	img = imgMap.get(path);
    		}else {
    			img = new Img();
    			img.setPath(path);
    			imgMap.put(path, img);
    			img_repository.saveAndFlush(img);
    		}
    	
    	step.setImg(img);
	    }

	   }
	    step_repository.saveAndFlush(step);
	}
}
}