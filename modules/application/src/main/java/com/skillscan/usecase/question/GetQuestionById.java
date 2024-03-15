package com.skillscan.usecase.question;

import com.skillscan.dto.question.QuestionDTO;

import java.util.UUID;

public interface GetQuestionById {
    public record InputPort(UUID id) {}

    public sealed interface OutputPort {
        public final record Ok(QuestionDTO question) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    public OutputPort execute(InputPort input);
}
