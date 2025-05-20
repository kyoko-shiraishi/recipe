package com.example.demo;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView post(@ModelAttribute("formModel") Recipe recipe, ModelAndView mav) {
		mav.setViewName("create");
		repository.saveAndFlush(recipe);
		return new ModelAndView("redirect:/");
	}
}
