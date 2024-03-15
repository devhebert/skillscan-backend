package com.skillscan.usecase.quizattempt;

import com.skillscan.dto.quizAttempt.QuizAttemptDTO;

import java.util.UUID;

public interface GetQuizAttemptById {
    public record InputPort(UUID id) {
    }

    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.Error ,OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(QuizAttemptDTO quizAttempt) implements OutputPort {}

        public final record NoResults() implements OutputPort {}

        public final record Error(String message) implements OutputPort {}

        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute(InputPort input);
}
