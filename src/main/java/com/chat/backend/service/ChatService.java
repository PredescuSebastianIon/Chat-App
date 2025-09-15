package com.chat.backend.service;

import com.chat.backend.model.ChatModel;
import com.chat.backend.model.UserChatModel;
import com.chat.backend.repositories.ChatRepository;
import com.chat.backend.repositories.UserChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserChatRepository userChatRepository;

    @Transactional
    public Long getChatId(Long senderId, Long receiverId) {
//        Optional <Long> chatId = userChatRepository.findDirectChatId(senderId, receiverId);

        String directKey = String.format(
                "%d-%d",
                Math.min(senderId, receiverId),
                Math.max(senderId, receiverId));
        Optional <UserChatModel> userChat = userChatRepository.findFirstByDirectKey(directKey);

        if (userChat.isPresent())
            return userChat.get().getChatId();

        return createChat(senderId, receiverId);
    }

    public Long createChat(Long senderId, Long receiverId) {
        String directKey = String.format(
                "%d-%d",
                Math.min(senderId, receiverId),
                Math.max(senderId, receiverId));

        ChatModel newChat = new ChatModel();
        chatRepository.save(newChat);

        UserChatModel newUserChatSender = new UserChatModel();
        newUserChatSender.setChatId(newChat.getChatId());
        newUserChatSender.setUserId(senderId);
        newUserChatSender.setDirectKey(directKey);

        UserChatModel newUserChatReceiver = new UserChatModel();
        newUserChatReceiver.setChatId(newChat.getChatId());
        newUserChatReceiver.setUserId(receiverId);
        newUserChatReceiver.setDirectKey(directKey);

        userChatRepository.save(newUserChatSender);
        userChatRepository.save(newUserChatReceiver);

        return newChat.getChatId();
    }
}
