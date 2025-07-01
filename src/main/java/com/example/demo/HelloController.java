package com.example.demo;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.repository.CookingRepository;
import com.example.demo.Services.RecipeService;


@Controller
public class HelloController {
	public final RecipeService recipeService;
	public HelloController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	// "/"にアクセスしたらmsgとコントロール、レコード一覧を表示
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("formModel") Recipe recipe,ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","ようこそ！");
		List<Recipe> list = recipeService.findAll();
		mav.addObject("data",list);
		return mav;
		
	}
	//特定リソースをGET
	@RequestMapping("/recipe/{id}")
	public ModelAndView show(ModelAndView mav,@PathVariable Long id) {
		mav.setViewName("recipe");
		Optional<Recipe> OptionalData = recipeService.findById(id);
		List<Step> stepData = recipeService.findByRecipeId(id);
		List<Amount> amountData = recipeService.findByRecipe(id);
		mav.addObject("steps",stepData);
		mav.addObject("amos",amountData);
		if(OptionalData.isPresent()) {
			Recipe data = OptionalData.get();
			mav.addObject("recipe",data);
		}else {
			mav.addObject("errorMsg","データがありません");
		}
		return mav;
	}
	
}





