package com.chat.backend.service;

import com.chat.backend.dto.SearchUsersDto;
import com.chat.backend.model.UserModel;
import com.chat.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final UserRepository userRepository;

    public SearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> searchUsers(SearchUsersDto searchUsersDto) {
        return userRepository.findTop25ByUsernameContainingIgnoreCase(searchUsersDto.getUsername());
    }
}
