package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import com.example.demo.DTO.RecipeRequest;
import com.example.demo.Services.RecipeService;
import java.util.List;
@Controller

public class NewMaterialController {
	public final RecipeService recipeService;
	public NewMaterialController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping(value="/mateMaster")
	public ModelAndView showMateMaster(ModelAndView mav) {
		mav.setViewName("mate_master");
		List<Mate> data = recipeService.showNames();
		mav.addObject("mates", data);
		return mav;
	}
	//マスタに材料追加
	@PostMapping("/add_mate")
	public ModelAndView AddMate(@RequestParam String name,ModelAndView mav) {
		String result = recipeService.addMate(name);
		String add_result;
		if(result.equals("success")) {
			add_result = "追加成功";
			mav.addObject("addedName",name);
		}else {
			add_result = "既にある材料です";
		}
		List<Mate> data = recipeService.showNames(); // ← ここで一覧を更新
	    mav.setViewName("mate_master");
	    mav.addObject("mates", data);
	    mav.addObject("result_message", add_result);
		return mav;
	}
}
