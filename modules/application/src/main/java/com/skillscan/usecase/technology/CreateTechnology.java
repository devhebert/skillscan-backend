package com.skillscan.usecase.technology;

import com.skillscan.model.enums.Category;

import java.util.UUID;

public interface CreateTechnology {
    public record InputPort(Category category, String name) {}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
