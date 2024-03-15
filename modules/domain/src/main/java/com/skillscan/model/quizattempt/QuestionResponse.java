package com.skillscan.model.quizattempt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class QuestionResponse {
    private UUID questionId;
    private String answer;

    public QuestionResponse() {
    }

    public QuestionResponse(UUID questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }
}
