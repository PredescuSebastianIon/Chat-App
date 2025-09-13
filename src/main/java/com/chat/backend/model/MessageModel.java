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

    @Column(name = "message_from", nullable = false, updatable = false)
    private Long messageFrom;

    @Column(name = "message_to", nullable = false, updatable = false)
    private Long messageTo;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;
}
