package com.example.oidc.controller;

import com.example.oidc.service.CurrentUser;
import com.example.oidc.service.UserEntity;
import com.example.oidc.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String profileForm(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.hasCompletedUserRegistration()) {
            return "redirect:/";
        }
        return "register-profile";
    }

    @PostMapping("/register-profile")
    public String registerProfile(UserForm form, HttpSession session) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken oauth2Token) {
            var newUser = userService.register(
                    form.username(),
                    oauth2Token.getAuthorizedClientRegistrationId(),
                    oauth2Token.getName()
            );
            var principle = new CurrentUser((OidcUser) oauth2Token.getPrincipal(), newUser);
            var updatedAuthentication = new OAuth2AuthenticationToken(
                    principle,
                    oauth2Token.getAuthorities(),
                    oauth2Token.getAuthorizedClientRegistrationId()
            );
            SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
            return "redirect:/";
        }
        return "redirect:/login?error";
    }
}
