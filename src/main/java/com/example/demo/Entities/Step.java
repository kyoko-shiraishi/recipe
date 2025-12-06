package com.example.demo.Entities;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
@Entity
@Table(name="steps")
public class Step {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column 
	private int step_number;
	@Column
	private String content;
	//Recipeクラスとリレーション(多対一)
	@ManyToOne
	@JoinColumn(name="recipe_id",nullable=false,referencedColumnName="id")
	private Recipe recipe;
		
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "img_id") // imgとの外部キー
	private Img img;
	
	public Long getId() {
		return this.id;
	}
	public int getStepNumber() {
		return this.step_number;
	}
	public String getContent() {
		return this.content;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStepNumber(int step_number) {
		this.step_number = step_number;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
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
