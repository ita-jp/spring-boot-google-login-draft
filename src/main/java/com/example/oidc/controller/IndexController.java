package com.example.oidc.controller;

import com.example.oidc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

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

    @GetMapping("/register-profile")
    public String profileForm() {
        return "register-profile";
    }

    @PostMapping("/register-profile")
    public String registerProfile(UserForm form) {
        userService.register(form.username());
        return "redirect:/";
    }
}
