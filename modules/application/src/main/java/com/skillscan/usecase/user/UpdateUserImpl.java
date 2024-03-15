package com.skillscan.usecase.user;

import com.skillscan.exception.user.ErrorMessages;
import com.skillscan.model.enums.Roles;
import com.skillscan.model.user.User;
import com.skillscan.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class UpdateUserImpl implements UpdateUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UpdateUserImpl.class);

    public UpdateUserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        System.out.println("inputport: " + inputPort.jobTitle());
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            return this.userRepository.findById(inputPort.id())
                    .map(user -> {
                        OutputPort validationOutput = this.validateInput(inputPort);
                        if (validationOutput != null) return validationOutput;

                        this.updateFields(user, inputPort);

                        User updatedUser = this.userRepository.save(user);

                        return new OutputPort.Ok(updatedUser.getId());
                    })
                    .orElse(new OutputPort.NotFound(ErrorMessages.USER_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private UpdateUser.OutputPort validateInput(InputPort inputPort) {
        if (inputPort.username() != null && inputPort.username().length() < 5) {
            return new UpdateUser.OutputPort.Error("O nome de usuário deve ter pelo menos 5 caracteres");
        }

        if (inputPort.password() != null && inputPort.password().length() < 6) {
            return new UpdateUser.OutputPort.Error("A senha deve ter pelo menos 6 caracteres");
        }

        if (inputPort.role() != null && !Roles.ADMIN.equals(inputPort.role()) && !Roles.COMMON.equals(inputPort.role()) && !Roles.SYSADMIN.equals(inputPort.role())) {
            return new UpdateUser.OutputPort.Error("Permissão inválida");
        }

        if (inputPort.jobTitle() == null )   {
            return new UpdateUser.OutputPort.Error("Titulo inválido");
        }

        return null;
    }

    private void updateFields(User user, InputPort inputPort) {
        if (inputPort.username() != null) {
            user.setUsername(inputPort.username());
        }

        if (inputPort.password() != null) {
            user.setPassword(this.passwordEncoder.encode(inputPort.password()));
        }

        if (inputPort.role() != null) {
            user.setRole(inputPort.role());
        }

        if (inputPort.jobTitle() != null) {
            user.setJobTitle(inputPort.jobTitle());
        }
    }
}
