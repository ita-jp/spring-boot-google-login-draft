package com.example.oidc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/settings")
    public String myPage() {
        return "settings";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        model.addAttribute("isLogoutSuccess", logout != null);
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("register-profile")
    public String registerProfilePage() {
        return "register-profile";
    }

}
