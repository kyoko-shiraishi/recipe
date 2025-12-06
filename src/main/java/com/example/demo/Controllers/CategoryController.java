package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import com.example.demo.DTO.RecipeDTO;
import com.example.demo.Services.RecipeService;
import com.example.demo.Entities.Category;
import java.util.List;
@Controller
public class CategoryController {
	public final RecipeService recipeService;
	public CategoryController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping(value="/cateMaster")
	public ModelAndView showCateMaster(ModelAndView mav) {
		mav.setViewName("category");
		List<Category> data = recipeService.showCateNames();
		mav.addObject("cates", data);
		return mav;
	}
	//マスタに材料追加
	@PostMapping("/add/category")
	public ModelAndView AddCate(@RequestParam String name,ModelAndView mav) {
		String result = recipeService.addCate(name);
		String add_result;
		if(result.equals("success")) {
			add_result = "追加成功";
			mav.addObject("addedCate",name);
		}else {
			add_result = "既にあるカテゴリーです";
		}
		List<Category> data = recipeService.showCateNames(); // ← ここで一覧を更新
	    mav.setViewName("category");
	    mav.addObject("cates", data);
	    mav.addObject("result_message", add_result);
		return mav;
	}
}
