package com.example.demo;
import jakarta.persistence.Column; 
import java.util.List;
import java.util.ArrayList;
import com.example.demo.*;
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
@Table(name="amounts")
//レシピ・材料中間テーブル
public class Amount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;
	@ManyToOne
	@JoinColumn(name="mate_id")
	private Mate mate;
	@Column
	private String amount;
	
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
	public Mate getMate() {
		return this.mate;
	}
	public void setMate(Mate mate) {
		this.mate=mate;
	}
	public  String getAmount() {
		return this.amount;
	}
	public void setAmount(String amount) {
		this.amount=amount;
	}
}
