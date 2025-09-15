package com.chat.backend.repositories;

import com.chat.backend.model.MessageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository <MessageModel, Long> {
    List<MessageModel> findAllByChatId(Long chatId);
    List<MessageModel> findAllByUserId(Long userId);

    Page<MessageModel> findByChatIdOrderByCreatedAtAsc(Long chatId, Pageable pageable);
}
