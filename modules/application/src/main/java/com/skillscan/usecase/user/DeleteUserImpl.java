package com.skillscan.usecase.user;

import com.skillscan.repository.UserRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteUserImpl implements DeleteUser {
    private final UserRepository userRepository;

    public DeleteUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public OutputPort execute(UUID id) {
        try {
            if (this.userRepository.findById(id).isEmpty()) return new OutputPort.NotFound();

            this. userRepository.deleteById(id);
            return new OutputPort.Ok();
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }
}