package com.chat.backend.controller;

import com.chat.backend.dto.SearchUsersDto;
import com.chat.backend.model.UserModel;
import com.chat.backend.service.SearchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ChatController {
    private final SearchService searchService;

    @GetMapping(path = "api/search/users")
    public String searchUsers(@ModelAttribute @Valid SearchUsersDto searchUsersDto,
                              BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (var err:result.getFieldErrors()) {
                errors.append(String.format("%s\n", err.getDefaultMessage()));
            }
            model.addAttribute("searchErrBackend", errors.toString());
        }

        try {
            List<UserModel> users = searchService.searchUsers(searchUsersDto);
            model.addAttribute("users", users);
            model.addAttribute("usernameSearched", searchUsersDto.getUsername());
            return "friends";
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }
}
