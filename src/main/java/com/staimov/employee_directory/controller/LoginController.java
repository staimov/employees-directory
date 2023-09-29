package com.staimov.employee_directory.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(HttpServletRequest request) {
        return "security/fancy-login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(HttpServletRequest request) {
        return "security/access-denied";
    }
}
