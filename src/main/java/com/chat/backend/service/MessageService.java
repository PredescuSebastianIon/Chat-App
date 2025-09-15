package com.chat.backend.service;

import com.chat.backend.dto.ChatMessageDto;
import com.chat.backend.dto.MessageDto;
import com.chat.backend.model.MessageModel;
import com.chat.backend.repositories.MessageRepository;
import com.chat.backend.repositories.UserChatRepository;
import com.chat.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @AllArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatMessageDto sendMessage(MessageDto messageDto) throws Exception {
        Long userId = getCurrentUserId();

        if (!userChatRepository.existsByChatIdAndUserId(messageDto.chatId(), userId)) {
            throw new Exception("Not participant in this chat!");
        }

        MessageModel message = new MessageModel();
        message.setUserId(userId);
        message.setChatId(messageDto.chatId());
        message.setContent(messageDto.content());

        MessageModel saved = messageRepository.save(message);

        ChatMessageDto payload = new ChatMessageDto(
                saved.getMessageId(),
                saved.getChatId(),
                saved.getUserId(),
                saved.getContent(),
                saved.getCreatedAt()
        );

        messagingTemplate.convertAndSend("/topic/chats/" + messageDto.chatId(), payload);
        return payload;
    }

    @Transactional(readOnly = true)
    public Page <ChatMessageDto> getMessage(Long chatId, int page, int size) throws Exception {
        Long userId = getCurrentUserId();

        if (!userChatRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new Exception("Not participant in this chat!");
        }

        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId, PageRequest.of(page, size))
                .map(m -> new ChatMessageDto(
                        m.getMessageId(),
                        m.getChatId(),
                        m.getUserId(),
                        m.getContent(),
                        m.getCreatedAt())
                );
    }

    private Long getCurrentUserId() throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long id;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            try {
                id = userRepository.findByUsername(auth.getName()).get().getId();
                return id;
            } catch (Exception exception) {
                throw new Exception(exception.getMessage());
            }
        }
        throw new Exception("User not authenticated");
    }


    //    New

    @Transactional(readOnly = true)
    public Page <ChatMessageDto> getMessage(Long chatId, int page, int size, java.security.Principal principal) throws Exception {
        Long userId = currentUserId(principal);

        if (!userChatRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new AccessDeniedException("Not participant in this chat!");
        }

        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId, PageRequest.of(page, size))
                .map(m -> new ChatMessageDto(
                        m.getMessageId(),
                        m.getChatId(),
                        m.getUserId(),
                        m.getContent(),
                        m.getCreatedAt())
                );
    }

    @Transactional
    public ChatMessageDto sendMessage(Long chatId, String content, java.security.Principal principal) throws Exception {
        Long userId = currentUserId(principal);

        if (!userChatRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new AccessDeniedException("Not participant in this chat!");
        }

        String text = content == null ? "" : content.trim();
        if (text.isBlank()) throw new IllegalArgumentException("Message content is empty");

        MessageModel m = new MessageModel();
        m.setUserId(userId);
        m.setChatId(chatId);
        m.setContent(text);
        var saved = messageRepository.save(m);

        var payload = new ChatMessageDto(
                saved.getMessageId(), saved.getChatId(), saved.getUserId(),
                saved.getContent(), saved.getCreatedAt()
        );

        messagingTemplate.convertAndSend("/topic/chats/" + chatId, payload);
        return payload;
    }

    private Long currentUserId(java.security.Principal principal) throws Exception {
        if (principal == null) throw new org.springframework.security.authentication.AuthenticationCredentialsNotFoundException("Not authenticated (WS)");
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .getId();
    }
}
