package com.example.demo.Controllers;
import java.util.List;
import java.util.Optional;
import com.example.demo.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.Services.RecipeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class HelloController {
	public final RecipeService recipeService;
	////SpringがBean部品だからと作ったRecipeServiceのインスタンスがrecipeServiceとして渡されてnewされる
	public HelloController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	// "/"にアクセスしたらmsgとコントロール、レコード一覧を表示
	@GetMapping("/")
	public ModelAndView index(@ModelAttribute("formModel") Recipe recipe,ModelAndView mav,HttpSession session) {
		mav.setViewName("index");
		String userName = (String)session.getAttribute("username");
		mav.addObject("username", userName);
		mav.addObject("msg","ようこそ！");
		List<Recipe> list = recipeService.findAll();
		mav.addObject("data",list);
		return mav;
		
	}
	//特定リソースをGET
	@GetMapping("/recipe/{id}")
	public ModelAndView show(ModelAndView mav,@PathVariable Long id) {
		mav.setViewName("recipe");
		Optional<Recipe> OptionalData = recipeService.findById(id);//レシピIDからレシピ取得
		List<Step> stepData = recipeService.findByRecipeId(id);//レシピIDからそのレシピのステップのリスト取得
		List<Recipe_Ingredient> recipe_ingredients = recipeService.findByRecipe(id);//レシピIDからそのレシピの材料のリスト取得
		//手順と材料も表示
		mav.addObject("steps",stepData);
		for (Step step : stepData) {
		    System.out.println("step = " + step); // まずstep自体を確認
		    if(step.getImg()!=null) {
		        System.out.println("step.img.filePath = " + step.getImg().getPath());
		    } else {
		        System.out.println("step.img is null");
		    }
		}

		mav.addObject("recipe_ingredients",recipe_ingredients);
		if(OptionalData.isPresent()) {
			Recipe data = OptionalData.get();
			//レシピを表示
			mav.addObject("recipe",data);
		}else {
			mav.addObject("errorMsg","データがありません");
		}
		return mav;
	}
	@GetMapping("/index")
    public String index(Model model) {
        // 例：表示するレシピをモデルにセット
        model.addAttribute("recipes", recipeService.findAll());
        return "index"; // src/main/resources/templates/index.html が必要
    }
}





