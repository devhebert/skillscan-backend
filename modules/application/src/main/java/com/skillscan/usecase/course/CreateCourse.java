package com.skillscan.usecase.course;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface CreateCourse {
    public record InputPort(@NotNull UUID technologyId, @NotNull String name, @NotNull String content, List<String> keywords) {}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
