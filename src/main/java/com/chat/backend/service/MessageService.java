package com.chat.backend.service;

import com.chat.backend.dto.MessageDto;
import com.chat.backend.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service @AllArgsConstructor
public class MessageService {
    private final UserRepository userRepository;

    public void sendMessage(@ModelAttribute @Valid MessageDto messageDto, BindingResult result, Model model) {

    }

    public void getMessage() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            try {
                id = userRepository.findByUsername(authentication.getName()).get().getId();
            } catch (Exception exception) {
                throw new Exception(exception.getMessage());
            }
        }
    }
}
