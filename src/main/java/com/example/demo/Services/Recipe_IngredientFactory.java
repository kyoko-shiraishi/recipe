package com.example.demo.Services;

import com.example.demo.DTO.RecipeDTO;
import com.example.demo.Entities.*;
import org.springframework.stereotype.Service;

@Service
public class Recipe_IngredientFactory {
	public Recipe_Ingredient build_recipe_ingredient(Recipe recipe,Ingredient ing,String rawName
			,RecipeDTO.Recipe_IngredientDTO ingdto) {
		Recipe_Ingredient newRecipe_Ingredient = new Recipe_Ingredient ();
        newRecipe_Ingredient.setRecipe(recipe);
        newRecipe_Ingredient.setIngredient(ing);
        newRecipe_Ingredient.setRawName(rawName);
        newRecipe_Ingredient.setQuantity(ingdto.getQuantity());
        return newRecipe_Ingredient;
	}

}
