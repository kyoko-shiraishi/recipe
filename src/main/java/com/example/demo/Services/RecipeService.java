package com.example.demo.Services;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.*;
import com.example.demo.repository.*;
import com.example.demo.DTO.*;
import com.example.demo.Step;
import java.util.stream.*;

@Service
public class RecipeService {
private final CookingRepository repository;
private final ImgRepository img_repository;
private final StepRepository step_repository;
private final MateRepository mate_repository;
private final CateRepository cate_repository;
private final AmountRepository amo_repository;
//コンストラクタインジェクションでサービスクラスからリポジトリ（データベース）を使えるようにする
public RecipeService(CookingRepository repository,ImgRepository img_repository,
		StepRepository step_repository,MateRepository mate_repository,CateRepository cate_repository,
		AmountRepository amo_repository) {
	this.repository = repository;
	this.img_repository = img_repository;
	this.step_repository = step_repository;
	this.mate_repository=mate_repository;
	this.cate_repository=cate_repository;
	this.amo_repository = amo_repository;
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
		
		//Imgレコード作成→メイン画像をレコード挿入＆保存
		Img mainImg = new Img();
		mainImg.setPath(recipe_request.getMainImg());
		img_repository.save(mainImg);
		//Recipeレコード作成→名前、コメント、保存したメイン画像をレコード挿入＆保存
		Recipe recipe = new Recipe();
		recipe.setName(recipe_request.getName()); 
		recipe.setComment(recipe_request.getComment());
		recipe.setMainImg(mainImg);
		repository.save(recipe);
		//DTOから手順リストと手順につかう画像リストをget
		List<StepRequest> description = recipe_request.getSteps();
		int stepNum = 1;
		Map<String,Img>ImgCache = new HashMap<>();
		for (int i = 0; i < description.size(); i++) {
		    StepRequest desc = description.get(i);
		    if (desc == null) continue; // ステップがなかったら処理をスキップして次のiに進む
		    Step step = new Step();
		    step.setStepNumber(stepNum++); 
		    step.setContent(desc.getContent());
		    step.setRecipe(recipe);
		    
		    // 画像の処理
		    String path = desc.getImg();
		    Img img;
		    if(path !=null&&!path.trim().isEmpty()) {
		    	 img = ImgCache.get(path);//マップにそのパスはあるか
		    	 if(img==null) {
		    		 img = img_repository.findByPath(path).orElse(null);//ない⇒Imgテーブルにそのパスはないか
		    		 if(img==null) {
		    			 //完全に新規の画像→Imgレコード作成＆挿入&マップにも追加して再利用可にする
		    			 img = new Img();
		    			 img.setPath(path);
		    			 img_repository.save(img);
		    			 ImgCache.put(path, img);
		    		 }    		 
		    		 }
		    	 step.setImg(img); 
		    }
		    step_repository.save(step);
		    
		}
		//Amountsテーブルにrecipe_requestからの値をセット
		//Materialの名前をもらう→DB検索→あったらAmount.setMaterial
		//RecipeのIDをそのままsetRecipeId
		//amountはそのまま文字列としてもらってsetAmount
		List<AmountRequest> amounts = recipe_request.getAmos();
		List<Amount> amountList = new ArrayList<>();
		List<String> tempList = new ArrayList<>();
		
		//Mateオブジェクト（レコード）の取り出し
		//リストの要素をひとつずつ変数にいれる
		//材料マスタに問い合わせた結果をOptionalMateとする
		for(int i=0;i<amounts.size();i++) {
			AmountRequest amo = amounts.get(i);
			Amount new_amo = new Amount();
			Optional<Mate> OptionalMate = mate_repository.findByName(amo.getMateName());
			if(OptionalMate.isPresent()) {
				Mate existMate = OptionalMate.get();
				new_amo.setMate(existMate);
			}else {
				//材料マスタにDTOから送られてきた材料がなかったとき
				//仮登録として材料名をMateクラスに登録して管理者に通知
				Mate tempMate = new Mate();
				tempMate.setTempMate(true);
				tempMate.setName(amo.getMateName());
				Mate savedTempMate=mate_repository.save(tempMate);
				new_amo.setMate(savedTempMate);
				tempList.add(savedTempMate.getName());
			}
			
			new_amo.setRecipe(recipe);
			new_amo.setAmount(amo.getAmount());
			amountList.add(new_amo);	
			
		}
		amo_repository.saveAll(amountList);
		//管理者に通知
		 for(String i:tempList) {
			 System.out.println(i);
		 }
}
public List<Amount> findByRecipe(Long id) {
	List<Amount> recipe_mate_amos = amo_repository.findByRecipeId(id);
	return recipe_mate_amos;
}
@Transactional
//データベースから取得した Recipe（レシピ情報）と Step（手順のリスト）を、
//フォーム送信用の RecipeRequest（DTO） に変換するためのメソッド
//ネスト構造のDTOであり子DTOにはステップDTOのリストをもつ。
public RecipeRequest convertToDto(Recipe recipe,List<Step> steps,List<Amount> amounts) {
	RecipeRequest dto = new RecipeRequest();
	dto.setId(recipe.getId());
	dto.setName(recipe.getName());
	dto.setComment(recipe.getComment());
	dto.setMainImg(recipe.getMainImg()!=null?recipe.getMainImg().getPath():null);
	//ユーザーからのステップentityデータをステップDTOのプロパティに詰めなおす
	//RecipeRequestはstepDTOのリストをStepsプロパティtとしてもつ
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
	//dto.setAmos(リスト)
	List<AmountRequest> amountDTOs = new ArrayList<>();
	for(Amount amount : amounts) {
		AmountRequest a = new AmountRequest();
		a.setId(amount.getId());
		a.setMateName(amount.getMate().getName());
		a.setRecipe(recipe);
		a.setAmount(amount.getAmount());
		amountDTOs.add(a);
	}
	dto.setAmos(amountDTOs);
	return dto;
}
@Transactional
public void editFromForm(RecipeRequest dto) {
	//メイン画像更新。パスが提示されるので
	//Imgテーブルにその画像がなかったら新しくセット
	//Imgテーブルにその画像があったらリポジトリからメイン画像パス取得
	String path = dto.getMainImg();
	Img img;
	if(img_repository.findByPath(path).isEmpty()) {
		img = new Img();
		img.setPath(path);
		
		img_repository.save(img);
	}else {
		img = img_repository.findByPath(path).get();
	}
	//レシピ本体を更新
	//DTOから最新状態にするレシピ(ID)をもらう
	//DTOからの値をname,commentにセット
	//メイン画像はさっきimgに入れたのでimgをセットし、保存
	Recipe recipe = repository.findById(dto.getId()).get();
	recipe.setName(dto.getName());
	recipe.setComment(dto.getComment());
	recipe.setMainImg(img);
	repository.save(recipe);
	Long id =recipe.getId();
	List<Step> old_steps = step_repository.findByRecipeId(id);
	Map<Long,Step> oldStepMap = old_steps.stream()
			.collect(Collectors.toMap(Step::getId, s -> s));
	List<Step> updatedSteps = new ArrayList<>();
	List<StepRequest> steplist =dto.getSteps();
	int stepIndex = 1;
	for(StepRequest stepDto:steplist) {
		Step step;
		if(stepDto.getId() !=null&&oldStepMap.containsKey(stepDto.getId())) {
			step = oldStepMap.get(stepDto.getId());
			oldStepMap.remove(stepDto.getId());
		}else {
			step = new Step();
			step.setRecipe(recipe);
		}
		step.setStepNumber(stepIndex++);
		step.setContent(stepDto.getContent());
		
		Img setImg;
		String stepImgpath = stepDto.getImg();
		if(stepImgpath != null&&!stepImgpath.isBlank()) {
		if(img_repository.findByPath(stepImgpath).isPresent()) {
			 setImg = img_repository.findByPath(stepImgpath).get();		
		}else {
			 setImg = new Img();
			setImg.setPath(stepImgpath);
			img_repository.save(setImg);
		}
		step.setImg(setImg);
		
		}
		//まだステップは保存されてない！
		//最新状態のステップリストに処理したステップを追加
		updatedSteps.add(step);
		
	}
	//フォームに含まれていなかった ID の Step だけがoldStepMap に残るようにしてある
	//残ったステップはユーザーの削除対象ということなのでDBから消しておく
	//フォームに来てたもの（IDあり or 新規）だけを保存する
	for(Step toDelete: oldStepMap.values()) {//古いほうののStep
		step_repository.delete(toDelete);;
	}
	for(Step new_step:updatedSteps) {
		step_repository.save(new_step);
		}
	//材料更新
	//DTOのamosリスト
	List<Amount> old_amounts = amo_repository.findByRecipeId(id);
	Map<Long,Amount> oldAmountMap = old_amounts.stream()
			.collect(Collectors.toMap(Amount::getId,s->s));
	List<Amount> updatedAmounts = new ArrayList<>();
	List<AmountRequest> amolists = dto.getAmos();
	List<String> tempList = new ArrayList<>();
	for(AmountRequest amoDTO:amolists) {
		Amount new_amo;
		if(amoDTO.getId() != null&&oldAmountMap.containsKey(amoDTO.getId())) {
			new_amo = oldAmountMap.get(amoDTO.getId());
			oldAmountMap.remove(amoDTO.getId());
		}else {
			new_amo = new Amount();
			new_amo.setRecipe(recipe);
		}
			//amoDTO.getMateName()→”卵”→”卵”を材料マスタから探してあればそのIDをゲットしてnew_amo.setMate(ID)
			//なかったら仮登録処理
			String mate_name = amoDTO.getMateName();
			Optional<Mate> OptionalMateName = mate_repository.findByName(mate_name);
			if(OptionalMateName.isPresent()) {
				Mate ExsitingMate = OptionalMateName.get();
				new_amo.setMate(ExsitingMate);
			}else {
				Mate tempMate = new Mate();
				tempMate.setTempMate(true);
				tempMate.setName(mate_name);
				Mate savedMate = mate_repository.save(tempMate);
				new_amo.setMate(savedMate);
				tempList.add(savedMate.getName());
			}
		
		
		
		new_amo.setAmount(amoDTO.getAmount());
		updatedAmounts.add(new_amo);
	}
	amo_repository.saveAll(updatedAmounts);
	for(Amount toDelete:oldAmountMap.values()) {
		amo_repository.delete(toDelete);
	}
	for(String temp:tempList) {
		System.out.println(temp);
	}
	}
	
	public List<Mate> showNames(){
		List<Mate> data = mate_repository.findAll().stream()
				.filter(mate->mate.isTempMate()==false)
				.collect(Collectors.toList());
		return data;
	}
	public List<Mate> showTempMates(){
		List<Mate> tempDate = mate_repository.findAll().stream()
				.filter(mate->mate.isTempMate()==true)
				.collect(Collectors.toList());
		return tempDate;
	}
	//引数に名前をもらい、mate_repositoryに既存でなければ挿入
	public String addMate(MateRequest dto) {
		Optional<Mate> existing = mate_repository.findByName(dto.getName());
		if(existing.isEmpty()) {
			Mate new_name = new Mate();
			new_name.setName(dto.getName());
			new_name.setCategory(dto.getCategory());
			new_name.setTempMate(false);
			mate_repository.save(new_name);
			return "success";
		}
		Mate existingMate = existing.get();
		if(existingMate.isTempMate()) {
			existingMate.setName(dto.getName());
			existingMate.setCategory(dto.getCategory());
			existingMate.setTempMate(false);
			mate_repository.save(existingMate);
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
	
	
	
	

