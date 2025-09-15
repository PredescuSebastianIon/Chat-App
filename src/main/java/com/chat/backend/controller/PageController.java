package com.chat.backend.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
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

    @GetMapping("/chat/{chatId}")
    public String chat(@PathVariable Long chatId) {
        return "chat";
    }
}

