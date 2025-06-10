package com.example.demo;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
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
	
	//Recipeクラスとリレーション(多対一)
	@ManyToOne
	@JoinColumn(name="recipe_id",nullable=false)
	private Recipe recipe;
}
