package com.chat.backend.controller;

import com.chat.backend.dto.MessageDto;
import com.chat.backend.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // client trimite la: /app/chat/{chatId}/send
    @MessageMapping("/chat/{chatId}/send")
    public void send(@DestinationVariable Long chatId,
                     @Payload MessageDto body,
                     java.security.Principal principal) throws Exception {
        messageService.sendMessage(chatId, body.content(), principal);
    }
}
