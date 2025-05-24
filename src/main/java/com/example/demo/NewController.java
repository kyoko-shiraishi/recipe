package com.example.demo;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;


import com.example.demo.repository.CookingRepository;

@Controller


public class NewController {
	@Autowired
	CookingRepository repository;
//Createページを表示
	@RequestMapping(value="/create")
	public ModelAndView create(ModelAndView mav) {
		mav.setViewName("create");
		mav.addObject("title","新しいレシピを作ってください！");
		
		mav.addObject("formModel", new Recipe()); 
		return mav;
	}
//サーバーに新しいデータを送信
	
	@Transactional
	@PostMapping("/create")
	public ModelAndView post(
	    @ModelAttribute("formModel") @Validated Recipe recipe, 
	    BindingResult result,
	    ModelAndView mav) {

	    ModelAndView res = null;
	    System.out.println(result.getFieldErrors());  // バリデーションエラーをログ出力

	    if (!result.hasErrors()) {
	        // エラーがなければ保存してトップページへリダイレクト
	        repository.saveAndFlush(recipe);
	        res = new ModelAndView("redirect:/");
	    } else {
	        // エラーがあれば作成ページに戻して、エラーメッセージを表示
	        mav.setViewName("create");
	        mav.addObject("msg", "入力内容にエラーがあります。再確認してください。");

	        // 必要に応じて、データ一覧も再読み込み（あれば）
	        // Iterable<Recipe> list = repository.findAll();
	        // mav.addObject("data", list);
//mav に設定した内容を、返す用の変数 res に代入する
	        res = mav;
	    }
	    return res;
	}

}
