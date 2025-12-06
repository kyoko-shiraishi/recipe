package com.example.demo.Controllers;
import org.springframework.stereotype.Controller;
import com.example.demo.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.DTO.UserDTO;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
@Controller
public class UserController {
	public final UserService userService;
	public  UserController(UserService userService) {
		this.userService = userService;
	}
	//登録ページの表示
	@GetMapping(value="/register_form")
	public ModelAndView show(ModelAndView mav) {
		mav.setViewName("register");
		mav.addObject("userRequest", new UserDTO()); 
		return mav;
	}
	//ユーザー登録
	@PostMapping("/register")
	public ModelAndView register(ModelAndView mav,@ModelAttribute("userRequest")UserDTO userRequest
			) {
		userService.register(userRequest);
		mav.setViewName("redirect:/");
		return mav;
	}
}
