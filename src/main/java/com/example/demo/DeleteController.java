package com.example.demo;
import java.util.List;



import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.Services.RecipeService;

@Controller
public class DeleteController {
	
	private final RecipeService recipeService;
	public DeleteController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView toCheck(ModelAndView mav,@PathVariable long id) {
		mav.setViewName("check");
		Recipe recipe=recipeService.findById(id).get();
		String recipe_name = recipe.getName();
		Long recipe_id = recipe.getId();
		mav.addObject("msgToCheck",recipe_name);
		mav.addObject("id",recipe_id);
		return mav;
	}
	@PostMapping("/delete/{id}")
	public ModelAndView remove(@PathVariable long id,ModelAndView mav) {
		recipeService.deleteById(id);
		mav = new ModelAndView("redirect:/");
		return mav;
	}
}
