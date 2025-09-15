package com.chat.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity @Table(name = "user_chat")
public class UserChatModel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "direct_key")
    private String directKey;
}
