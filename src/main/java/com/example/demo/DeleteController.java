package com.example.demo;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteController {
	@Autowired
	CookingRepository repository;
	//特定のIDのリソースを画面表示
	@RequestMapping(value="/delete/{id}",method= RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id,ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("msg","どのレシピを削除しますか？");
		Optional<Recipe> data = repository.findById((long)id);
		if(data.isPresent()) {
			mav.addObject("formModel",data.get());
		}else if(data.isEmpty()){
			mav.addObject("formModel",null);
			mav.addObject("message","データがみつかりません");
		}
		return mav;
	}
	
	@PostMapping("/delete_clicked")
	@Transactional
	public ModelAndView remove(@RequestParam long id,ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}
}
