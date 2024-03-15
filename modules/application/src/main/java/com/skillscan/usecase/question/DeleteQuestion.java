package com.skillscan.usecase.question;

import java.util.UUID;

public interface DeleteQuestion {
    public record InputPort(UUID id) {}

    sealed interface OutputPort permits OutputPort.Error, OutputPort.NotFound, OutputPort.Ok {
        record Ok() implements OutputPort {}
        record NotFound() implements OutputPort {}
        record Error(String message) implements OutputPort {}
    }

    OutputPort execute(UUID id);
}
