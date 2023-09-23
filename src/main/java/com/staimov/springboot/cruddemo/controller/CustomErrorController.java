package com.staimov.springboot.cruddemo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class CustomErrorController extends AbstractErrorController {
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    public CustomErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes, Collections.emptyList());
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> body =
                this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception e = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        String message = "Something went wrong!";
        logger.error(message);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                message = "Sorry, we couldn't find the page you're looking for";
            }

            logger.error("HTTP Status: {}", status);
        }

        if (e != null) {
            logger.error("An exception occurred!", e);
        }

        model.addAttribute("message", message);
        model.addAttribute("status", status);
        model.addAttribute("exception", e);

        return "error";
    }
}
