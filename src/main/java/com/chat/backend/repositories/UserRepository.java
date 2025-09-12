package com.chat.backend.repositories;

import java.util.Optional;

import com.chat.backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
