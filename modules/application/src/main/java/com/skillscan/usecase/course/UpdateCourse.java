package com.skillscan.usecase.course;

import java.util.List;
import java.util.UUID;

public interface UpdateCourse {
    public record InputPort(UUID id, UUID technologyId , String name, String content, List<String> keywords) {}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.NotFound, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record NotFound(String message) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
