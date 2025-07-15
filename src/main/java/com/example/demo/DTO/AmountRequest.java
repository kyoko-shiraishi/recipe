package com.example.demo.DTO;
import com.example.demo.*;
public class AmountRequest {
	private Long id;
	private Recipe recipe;
	private String mateName;
	private String amount;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getMateName() {
		return this.mateName;
	}
	public void setMateName(String mate) {
		this.mateName=mate;
	}
	public  String getAmount() {
		return this.amount;
	}
	public void setAmount(String amount) {
		this.amount=amount;
	}
	public Recipe getRecipe() {
		return this.recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
