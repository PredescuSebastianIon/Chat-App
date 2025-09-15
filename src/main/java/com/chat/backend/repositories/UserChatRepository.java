package com.chat.backend.repositories;

import com.chat.backend.model.UserChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRepository extends JpaRepository<UserChatModel, Long> {
    List<UserChatModel> findAllByChatId(Long chatId);
    List<UserChatModel> findAllByUserId(Long userId);

    boolean existsByChatIdAndUserId(Long chatId, Long userId);

    Optional<UserChatModel> findFirstByDirectKey(String directKey);

    @Query("""
    select min(uc1.chatId)
    from UserChatModel uc1, UserChatModel uc2
    where uc1.chatId = uc2.chatId
      and uc1.userId = :senderId
      and uc2.userId = :receiverId
  """)
    Optional<Long> findDirectChatId(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
