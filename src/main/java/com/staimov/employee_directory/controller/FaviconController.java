package com.staimov.employee_directory.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Hidden
public class FaviconController {
    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
        // to avoid the 404 error
    }
}
