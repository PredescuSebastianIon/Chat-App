package com.chat.backend.dto;

// ce TRIMIȚI către clienți (WS/REST) după persistare
public record ChatMessageDto(
        Long messageId,
        Long chatId,
        Long userId,
        String content,
        java.time.Instant createdAt
) {}
