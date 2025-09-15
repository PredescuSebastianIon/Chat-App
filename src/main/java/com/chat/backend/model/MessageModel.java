package com.chat.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity @Table(name = "message")
@Getter @Setter
public class MessageModel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "chat_id", nullable = false, updatable = false)
    private Long chatId;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;
}
