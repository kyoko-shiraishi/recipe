package com.example.demo;
import java.util.List;
//レシピ名・コメント・手順リスト・画像リストを持つDTO
public class RecipeRequest {
	private String name;
	private String comment;
	private List<String> step_description;
	private List<String> step_img;
	
	public String getName() {
		return this.name;
	}
	public String getcomment() {
		return this.comment;
	}
	public List<String> getStepDescription(){
		return this.step_description;
	}
	public List<String> getStepImg(){
		return this.step_img;
	}
	public void setNmae(String name) {
		this.name = name;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setStepDescription(List<String> step_description) {
		this.step_description = step_description;
	}
	public void setStepImg(List<String> step_img) {
		this.step_img = step_img;
	}
}
