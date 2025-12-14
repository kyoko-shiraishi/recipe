package com.example.demo.Services;
import com.example.demo.DTO.*;
import com.example.demo.Entities.Recipe;
import com.example.demo.Entities.Img;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class NewRecipeService {
	private NewRecipeService() {}
	
	public static void of(RecipeDTO dto,Recipe r,Img mainImgEntity) {

		r.setName(dto.getName());
	    r.setComment(dto.getComment());
	    r.setMainImg(mainImgEntity);
	}
	
}
