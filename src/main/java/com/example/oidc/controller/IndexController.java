package com.example.oidc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/settings")
    public String myPage() {
        return "settings";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
