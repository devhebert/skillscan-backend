package com.skillscan.repository;

import com.skillscan.model.user.User;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserDetails> findByUsername(String username);
    User save(User user);
    List<User> all();
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
}
