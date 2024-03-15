package com.skillscan.usecase.question;

import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Answer;

import java.util.List;
import java.util.UUID;

public interface UpdateQuestion {
    public record InputPort(UUID id, String questionText, List<Answer> answers, Difficulty difficulty, UUID technologyId) { }

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.NotFound, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record NotFound(String message) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
