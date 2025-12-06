package com.example.demo.Services;
import com.example.demo.DTO.*;
import com.example.demo.Entities.Recipe;
import com.example.demo.Entities.Img;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class NewRecipeService {
	
	public Recipe createRecipe(RecipeDTO dto,Img mainImgEntity) {
		Recipe recipe = new Recipe();
		recipe.setName(dto.getName());
	    recipe.setComment(dto.getComment());
	    recipe.setMainImg(mainImgEntity);
	    return recipe;
	}
	
}
