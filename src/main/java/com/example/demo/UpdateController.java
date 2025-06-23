package com.example.demo;
import java.util.List;

import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;
import com.example.demo.Services.RecipeService;

@Controller
public class UpdateController {
	public final RecipeService recipeService;
public UpdateController(RecipeService recipeService) {
	this.recipeService = recipeService;
}
@RequestMapping("/edit/{id}")
public ModelAndView edit(ModelAndView mav,@PathVariable int id) {
	mav.setViewName("edit");
	Optional<Recipe> data = recipeService.findById((long)id);
	List<Step> steps = recipeService.findByRecipeId(id);
	mav.addObject("steps",steps);
	if(data.isPresent()) {
		mav.addObject("recipe",data.get());	
		
	}else if(data.isEmpty()){
		mav.addObject("data",null);
		mav.addObject("message","データが見つかりません");
	}
	
	return mav;
}
//保存
@PostMapping("/edit")
public ModelAndView update(@ModelAttribute Recipe recipe,ModelAndView mav) {
	recipeService.saveAndFlush(recipe);
	return new ModelAndView("redirect:/");
}
}
