package com.example.demo.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import com.example.demo.DTO.*;
import com.example.demo.Services.RecipeService;
import com.example.demo.Services.SynonymService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.example.demo.Entities.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class NewMaterialController {
	public final RecipeService recipeService;
	public final SynonymService synonymService;
	public NewMaterialController(RecipeService recipeService,SynonymService synonymService) {
		this.recipeService = recipeService;
		this.synonymService = synonymService;
	}
	
	@RequestMapping(value="/mateMaster")
	public ModelAndView showMateMaster(ModelAndView mav) {
		mav.setViewName("mate_master");
		List<Ingredient> all_ingredient = synonymService.all_ingredients();
		List<IngInfoDTO> dtos = synonymService.convertToDTOs(all_ingredient);
		mav.addObject("IngInfo",dtos);
		return mav;
	}
	//マスタに材料追加
	@PostMapping("/add_mate")
	public String AddMate(ModelAndView mav,@ModelAttribute("mateRequest") IngredientDTO dto,
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
