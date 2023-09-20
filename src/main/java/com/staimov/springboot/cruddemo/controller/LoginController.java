package com.staimov.springboot.cruddemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "/security/fancy-login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "/security/access-denied";
    }
}
