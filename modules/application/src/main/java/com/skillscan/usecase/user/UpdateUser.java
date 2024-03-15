package com.skillscan.usecase.user;

import com.skillscan.model.enums.JobTitle;
import com.skillscan.model.enums.Roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UpdateUser {
    public record InputPort(@NotBlank @NotNull UUID id, String username, String password, Roles role, JobTitle jobTitle) {}

    sealed interface OutputPort permits OutputPort.Error, OutputPort.NotFound, OutputPort.Ok {
        public final record Ok(UUID id) implements OutputPort {}
        public final record NotFound(String message) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
