package com.example.demo;
import jakarta.persistence.Column; 

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
@Entity
@Table(name="steps")
public class Step {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	@Column 
	private int step_number;
	@Column
	private String content;
	@Column 
	
	public long getId() {
		return this.id;
	}
	public int getStepNumber() {
		return this.step_number;
	}
	public String getContent() {
		return this.content;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStepNumber(int step_number) {
		this.step_number = step_number;
	}
	public void setContent(String content) {
		this.content = content;
	}
	//Recipeクラスとリレーション(多対一)
	@ManyToOne
	@JoinColumn(name="recipe_id",nullable=false)
	private Recipe recipe;
	
	@OneToOne
	@JoinColumn(name="img_id",nullable=true)
	private Img img;
	
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		//内部的に recipe.getId() が使われて 外部キーrecipe_id に採番済みのレシピIDが代入される
	}
	public Img getImg() {
		return this.img;
	}
	public void setImg(Img img) {
		this.img = img;
	}
}
