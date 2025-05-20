package com.example.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repository.CookingRepository;


@Controller
public class HelloController {
	@Autowired
	CookingRepository repository;
	// "/"にアクセスしたらmsgとコントロール、レコード一覧を表示
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("formModel") Recipe recipe,ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","ようこそ！");
		List<Recipe> list = repository.findAll();
		mav.addObject("data",list);
		return mav;
		
	}
	
	
}





