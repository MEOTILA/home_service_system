package com.example.home_service_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        // Since we're using static HTML, this just needs to return the path
        // Spring will automatically serve login.html from static directory
        return "forward:/login.html";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "forward:/index.html";
    }
}
