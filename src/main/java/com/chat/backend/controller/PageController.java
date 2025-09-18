package com.chat.backend.controller;
import com.chat.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class PageController {

    @GetMapping(path = "/home")
    public String Home() throws IOException {
        return "home";
    }

    @GetMapping(path = "/login")
    public String Login() throws IOException {
        return "login";
    }

    @GetMapping(path = "/register")
    public String Register() throws IOException {
        return "register";
    }

    @GetMapping(path = "/friends")
    public String Friends() throws IOException {
        return "friends";
    }

//    @GetMapping("/chat/{chatId}")
//    public String chat(@PathVariable Long chatId) {
//        return "chat";
//    }

    private final UserRepository userRepository;

    @GetMapping("/chat/{chatId}")
    public String chat(@PathVariable Long chatId, Model model, Principal p) {
        Long currentUserId = userRepository.findByUsername(p.getName()).orElseThrow().getId();
        model.addAttribute("currentUserId", currentUserId);
        return "chat";
    }

    @GetMapping("/chat2/{chatId}")
    public String getChat(@PathVariable Long chatId, Model model, Principal p) {
        Long currentUserId = userRepository.findByUsername(p.getName()).orElseThrow().getId();
        model.addAttribute("currentUserId", currentUserId);
        return "chat2";
    }
}
