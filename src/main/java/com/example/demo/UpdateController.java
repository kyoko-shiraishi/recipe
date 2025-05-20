package com.example.demo;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;

@Controller
public class UpdateController {
@Autowired
CookingRepository repository;
@RequestMapping("/edit/{id}")
public ModelAndView edit(ModelAndView mav,@ModelAttribute Recipe recipe,@PathVariable int id) {
	mav.setViewName("edit");
	Optional<Recipe> data = repository.findById((long)id);
	mav.addObject("formModel",data.get());
	return mav;
}
//保存
@PostMapping("/edit")
@Transactional
public ModelAndView update(@ModelAttribute Recipe recipe,ModelAndView mav) {
	repository.saveAndFlush(recipe);
	return new ModelAndView("redirect:/");
}
}
