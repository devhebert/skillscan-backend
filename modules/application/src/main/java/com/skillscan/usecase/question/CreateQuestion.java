package com.skillscan.usecase.question;

import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Answer;

import java.util.List;
import java.util.UUID;

public interface CreateQuestion {
    public record InputPort(String questionText, List<Answer> answerList, Difficulty difficulty, UUID technologyId) {}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
