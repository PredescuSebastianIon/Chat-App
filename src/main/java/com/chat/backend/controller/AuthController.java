package com.chat.backend.controller;

import com.chat.backend.dto.RegisterDto;
import com.chat.backend.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public String createUser(@ModelAttribute @Validated RegisterDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            StringBuilder errorsString = new StringBuilder();
            for (var error:result.getFieldErrors()) {
                errorsString.append(String.format("%s %s\n", error.getField(), error.getDefaultMessage()));
            }
            model.addAttribute("regErrorBackend", errorsString.toString());
            return "register";
        }

        try {
            authService.registerService(user);
            return "redirect:/login";
        } catch (IllegalArgumentException error) {
            model.addAttribute("regErrorBackend", error.getMessage());
            return "register";
        }
    }
}
