package com.example.demo.Controllers;
import java.util.List;



import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entities.Recipe;
import com.example.demo.repository.RecipeRepository;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.Services.RecipeService;

@Controller
public class DeleteController {
	
	private final RecipeService recipeService;
	public DeleteController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}


	@PostMapping("/delete/{id}")
	public ModelAndView remove(@PathVariable Long id,ModelAndView mav) {
		recipeService.deleteById(id);
		mav = new ModelAndView("redirect:/");
		return mav;
	}
}
