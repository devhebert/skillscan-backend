package com.skillscan.usecase.user;

import java.util.UUID;

public interface DeleteUser {
    sealed interface OutputPort permits OutputPort.Error, OutputPort.NotFound, OutputPort.Ok {
        record Ok() implements OutputPort {}
        record NotFound() implements OutputPort {}
        record Error(String message) implements OutputPort {}
    }

    OutputPort execute(UUID id);
}