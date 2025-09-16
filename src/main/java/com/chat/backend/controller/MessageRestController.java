package com.chat.backend.controller;

import com.chat.backend.dto.ChatMessageDto;
import com.chat.backend.dto.MessageDto;
import com.chat.backend.service.MessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chats")
public class MessageRestController {

    private final MessageService messageService;

    // GET /api/chats/{chatId}/messages?page=0&size=50
    @GetMapping("/{chatId}/messages")
    public Page<ChatMessageDto> list(@PathVariable Long chatId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "50") int size,
                                     Principal principal) throws Exception {
        return messageService.getMessage(chatId, page, size, principal);
    }

    // POST /api/chats/{chatId}/messages  body: { "content": "Salut" }
    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ChatMessageDto> send(@PathVariable Long chatId,
                                               @RequestBody @Valid MessageDto body,
                                               Principal principal) throws Exception {
        ChatMessageDto saved = messageService.sendMessage(chatId, body.content(), principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
