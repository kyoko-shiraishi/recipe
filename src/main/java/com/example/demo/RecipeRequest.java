package com.example.demo;
import java.util.List;
import java.util.ArrayList;
//レシピ名・コメント・手順リスト・画像リストなどフォームからの情報を格納するDTOプロパティ
public class RecipeRequest {
	private String name;
	private String comment;
	private List<String> stepDescription= new ArrayList<>();
	private List<String> stepImg = new ArrayList<>();
	private String mainImg; //画面のフォームからパス（文字列）をうけとる
//他のクラスがDTOからDTOプロパティの情報にアクセスできるようにgetter/setterを設定	
	public String getName() {
		return this.name;
	}
	public String getComment() {
		return this.comment;
	}
	public String getMainImg() {
		return this.mainImg;
	}
	
	public List<String> getStepDescription(){
		return this.stepDescription;
	}
	public List<String> getStepImg(){
		return this.stepImg;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	public void setStepDescription(List<String> step_description) {
		this.stepDescription = step_description;
	}
	public void setStepImg(List<String> step_img) {
		this.stepImg = step_img;
	}
}
