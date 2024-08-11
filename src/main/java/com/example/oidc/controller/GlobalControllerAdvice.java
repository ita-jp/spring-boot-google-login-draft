package com.example.oidc.controller;

import com.example.oidc.service.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("currentUser")
    public CurrentUser currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser;
    }
}
