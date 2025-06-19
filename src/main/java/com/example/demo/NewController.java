package com.example.demo;
import jakarta.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;


import com.example.demo.Services.RecipeService;

@Controller


public class NewController {
	public final RecipeService recipeService;
	public NewController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
//Createページを表示
	@RequestMapping(value="/create")
	public ModelAndView create(ModelAndView mav) {
		RecipeRequest recipeRequest = new RecipeRequest();
		// step_imgに空文字列をいくつか用意しておく
	    recipeRequest.getStepImg().add(null); // step_img[0]
	    recipeRequest.getStepDescription().add(null);
		mav.setViewName("create");
		mav.addObject("title","新しいレシピを作ってください！");
		
		mav.addObject("recipe_request", recipeRequest); 
		return mav;
	}
//サーバーに新しいデータを送信
	
	
	@PostMapping("/create")
	public ModelAndView post(
	    @ModelAttribute("recipe_request")  RecipeRequest recipe_request, 
	    ModelAndView mav) {

			ModelAndView res = mav;
	        recipeService.createFromForm(recipe_request);
	        res = new ModelAndView("redirect:/");
	        return res;
	    }
	   
	}


