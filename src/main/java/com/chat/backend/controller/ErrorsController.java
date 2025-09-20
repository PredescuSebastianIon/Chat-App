package com.chat.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;

@Controller
public class ErrorsController implements ErrorController {
    @RequestMapping("/error")
    public String handleErrors(HttpServletResponse http) {
        if (http.getStatus() == 404) {
            return "redirect:/home";
        }
        return "redirect:/home";
    }
}
