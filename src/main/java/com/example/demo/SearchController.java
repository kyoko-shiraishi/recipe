package com.example.demo;

import org.springframework.stereotype.Controller;

import jakarta.transaction.Transactional;

import com.example.demo.Services.RecipeService;
import com.example.demo.repository.CookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Services.RecipeService;

import java.util.List;





 
 @Controller
 public class SearchController {
	 public final RecipeService recipeService;
		public SearchController(RecipeService recipeService) {
			this.recipeService = recipeService;
		}
	 
 	@RequestMapping(value="/search",method=RequestMethod.GET)
 	//method="get" → ブラウザのURLにクエリパラメータが付く（例：/search?dish-name=鉄粉おにぎり）
 	
 	
 	public  ModelAndView search(@ModelAttribute("formModel") Recipe recipe,
 			ModelAndView mav,@RequestParam("dish-name") String keyword) {
 		mav.setViewName("index");
 		mav.addObject("hoge",keyword);
 		List<Recipe> search_rslt = recipeService.findByNameContaining(keyword);
 		mav.addObject("data" ,search_rslt);
 		return mav;
 	}
 }

