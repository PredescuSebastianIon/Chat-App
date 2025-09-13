package com.chat.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "chat")
@Getter @Setter
public class ChatModel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id")
    private Long chatId;
}
