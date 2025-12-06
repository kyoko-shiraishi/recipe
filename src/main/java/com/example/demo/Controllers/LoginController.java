package com.example.demo.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
public class LoginController {
	@RequestMapping("/login")
    public String login() {
        return "login"; // ← templates/login.html を表示する
    }
}
