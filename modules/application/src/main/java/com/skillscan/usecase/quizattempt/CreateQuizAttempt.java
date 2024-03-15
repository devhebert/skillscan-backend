package com.skillscan.usecase.quizattempt;

import com.skillscan.dto.quizAttempt.QuizResponseDTO;
import com.skillscan.model.quizattempt.QuestionResponse;

import java.util.List;
import java.util.UUID;

public interface CreateQuizAttempt {
    public record InputPort(UUID userId, UUID technologyId ,List<QuestionResponse> questionResponseList) {}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id, QuizResponseDTO quizResponse) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
