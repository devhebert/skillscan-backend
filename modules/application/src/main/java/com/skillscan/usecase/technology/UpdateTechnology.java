package com.skillscan.usecase.technology;

import com.skillscan.model.enums.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UpdateTechnology {
    public record InputPort(@NotBlank @NotNull UUID id, Category category, String name) { }

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.NotFound, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record NotFound(String message) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
