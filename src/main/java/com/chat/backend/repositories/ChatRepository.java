package com.chat.backend.repositories;

import com.chat.backend.model.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository <ChatModel, Long> {
    Optional<ChatModel> findByChatId(Long chatId);
}
