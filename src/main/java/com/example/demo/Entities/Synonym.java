package com.example.demo.Entities;
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
@Table(name="synonims")
public class Synonym {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String name;
	
	
	@ManyToOne
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;
	
	//コンストラクタ（引数なし）
	public Synonym() {}
	
	//コンストラクタ（引数あり）
	public Synonym(String name,Ingredient ingredient) {
			this.name = name;
			this.ingredient = ingredient;
	}
		
	public Long getId() {
		        return id;
	}

	public String getName() {
		        return name;
	}

	public void setName(String name) {
		        this.name = name;
	}

	public Ingredient getIngredient() {
		        return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		        this.ingredient = ingredient;
	}
}


