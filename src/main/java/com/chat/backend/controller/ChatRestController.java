package com.chat.backend.controller;

import com.chat.backend.repositories.UserRepository;
import com.chat.backend.service.ChatService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatRestController {
    private final ChatService chatService;
    private final UserRepository userRepository;

    @PostMapping("/direct")
    public Map<String, Long> direct(@RequestBody Map<String, Long> body) {
        Long receiverId = body.get("receiverId");
        Long senderId = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow().getId();
        Long chatId = chatService.getChatId(senderId, receiverId);
        return Map.of("chatId", chatId);
    }
}
