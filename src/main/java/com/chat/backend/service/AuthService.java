package com.chat.backend.service;

import java.util.Optional;

import com.chat.backend.dto.RegisterDto;
import com.chat.backend.model.UserModel;
import com.chat.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    AuthService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <UserModel> user = repository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }

    public UserModel registerService(RegisterDto registerDto) {
        if (repository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("This email is already in use");
        }

        if (repository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("This username is already in use");
        }

        UserModel newUser = new UserModel();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        return repository.save(newUser);
    }
}
