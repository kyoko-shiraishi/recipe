package com.example.demo.Entities;
import jakarta.persistence.Column; 
import java.util.List;
import java.util.ArrayList;
import com.example.demo.Entities.*;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;


@Entity
@Table(name="recipe_ingredients")
//レシピ・材料中間テーブル
public class Recipe_Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;
	@ManyToOne
	@JoinColumn(name="ingredient_id")
	private Ingredient ingredient;
	@Column
	private String rawName;
	@Column
	private String quantity;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public Recipe getRecipe() {
		return this.recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe=recipe;
	}
	public Ingredient getIngredient() {
		return this.ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient=ingredient;
	}
	public String getRawName() {
		return this.rawName;
	}
	public void setRawName(String name) {
		this.rawName = name;
	}
	public  String getQuantity() {
		return this.quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity=quantity;
	}
}
