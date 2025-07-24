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
import com.example.demo.DTO.MateRequest;
import com.example.demo.Services.RecipeService;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		List<Mate> tempDate = recipeService.showTempMates();
		List<Category> categoeies = recipeService.showCateNames();
		
		mav.addObject("mates", data);
		mav.addObject("tempDates",tempDate);
		mav.addObject("mateRequest", new MateRequest());
		mav.addObject("categories", categoeies);
		return mav;
	}
	//マスタに材料追加
	@PostMapping("/add_mate")
	public String AddMate(ModelAndView mav,@ModelAttribute("mateRequest") MateRequest dto,
			RedirectAttributes redirectAttributes) {
		String result = recipeService.addMate(dto);
		
		if(result.equals("success")) {
			redirectAttributes.addFlashAttribute("addedName",dto.getName());
			redirectAttributes.addFlashAttribute("result_message","追加成功");
		}else {
			redirectAttributes.addFlashAttribute("addedName", dto.getName());
	        redirectAttributes.addFlashAttribute("result_message", "既にある材料です");
		}
		 return "redirect:/mateMaster"; // GETにリダイレクト
	}
}
