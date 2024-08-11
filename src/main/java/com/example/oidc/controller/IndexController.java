package com.example.oidc.controller;

import com.example.oidc.config.SocialLoginSessionData;
import com.example.oidc.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/settings")
    public String myPage() {
        return "settings";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        model.addAttribute("isLogoutSuccess", logout != null);
        model.addAttribute("isError", error != null);
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
    public String registerProfile(UserForm form, HttpSession session) {
        var sessionData = (SocialLoginSessionData) session.getAttribute(
                SocialLoginSessionData.SESSION_ATTRIBUTE_NAME
        );
        session.removeAttribute(SocialLoginSessionData.SESSION_ATTRIBUTE_NAME);
        if (sessionData == null) {
            return "redirect:/login?error";
        }
        userService.register(
                form.username(),
                sessionData.provider(),
                sessionData.subject()
        );
        return "redirect:/";
    }
}
