package com.skillscan.usecase.user;

import com.skillscan.exception.user.ErrorMessages;
import com.skillscan.model.user.User;
import com.skillscan.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class CreateUserImpl implements CreateUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(CreateUserImpl.class);

    public CreateUserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try{
            if (inputPort == null) {
                return new OutputPort.Error(ErrorMessages.INVALID_INPUT);
            }

            if (this.userRepository.findByUsername(inputPort.username()).isPresent()) {
                return new OutputPort.Error(ErrorMessages.USER_ALREADY_EXISTS);
            }

            OutputPort validationOutput = validateUser(inputPort);
            if (validationOutput != null) return validationOutput;

            User saved = this.userRepository.save(new User(inputPort.username(), this.passwordEncoder.encode(inputPort.password()), inputPort.role(), inputPort.jobTitle()));
            logger.info("User created, cache invalidated");
            return new OutputPort.Ok(saved.getId());
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateUser(InputPort user) {
        if (user.username() == null || user.username().trim().isEmpty()) {
            return new OutputPort.Error("O username não pode ser nulo ou vazio.");
        }

        if (user.password() == null || user.password().trim().isEmpty()) {
            return new OutputPort.Error("O password não pode ser nulo ou vazio.");
        }

        if (user.role() == null) {
            return new OutputPort.Error("O tipo de usuário não pode ser nulo.");
        }

        return null;
    }
}
