package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;

import jakarta.transaction.Transactional;
import com.example.demo.Services.RecipeService;
import com.example.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Services.RecipeService;
import com.example.demo.Services.SynonymService;
import com.example.demo.Entities.*;

import java.util.List;





 
 @Controller
 public class SearchController {
	 public final RecipeService recipeService;
	 public final SynonymService synonymService;
		public SearchController(RecipeService recipeService,SynonymService synonymService) {
			this.recipeService = recipeService;
			this.synonymService = synonymService;
		}
	 
 	@RequestMapping(value="/search",method=RequestMethod.GET)
 	//method="get" → ブラウザのURLにクエリパラメータが付く（例：/search?dish-name=鉄粉おにぎり）
 	
 	
 	public  ModelAndView search(ModelAndView mav,@RequestParam("dish-name") String keyword) {
 		mav.setViewName("index");
 		mav.addObject("hoge",keyword);
 		NormalizeResult res = synonymService.normalize(keyword);
 		List<Recipe> search_rslt = recipeService.findByNameContaining(res.canonicalName());
 		mav.addObject("data" ,search_rslt);
 		return mav;
 	}
 }

