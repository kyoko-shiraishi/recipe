package com.example.demo.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import com.example.demo.DTO.RecipeDTO;
import com.example.demo.Services.RecipeService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NewController {
	public final RecipeService recipeService;
	public NewController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
//Createページを表示
	@RequestMapping(value="/create")
	public ModelAndView create(ModelAndView mav) {
		
		RecipeDTO recipeDTO = new RecipeDTO();
		mav.setViewName("create");
		mav.addObject("title","新しいレシピを作ってください！");
		mav.addObject("recipeDTO", recipeDTO); 
		return mav;
	}
//サーバーに新しいデータを送信
	
	
	@PostMapping("/create")
	public String post(
	    @ModelAttribute("recipe_request")  RecipeDTO recipe_request, 
	   RedirectAttributes redirectAttributes) {
		try {
        recipeService.createFromForm(recipe_request);
        // // リダイレクト後の画面で使う「成功メッセージ」を一時保存
        redirectAttributes.addFlashAttribute("result","新しいレシピを追加！");
        	}
			catch(Exception e){
				// エラーメッセージも同じように一時保存
				redirectAttributes.addFlashAttribute("exception","エラーが発生(´;ω;｀)");
			}
		// 登録処理が終わったらトップページにリダイレクト
			return "redirect:/";
			
	    }
	   
	}


