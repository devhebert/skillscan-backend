package com.skillscan.service;

import com.skillscan.dto.question.QuestionResponseDTO;
import com.skillscan.model.quizattempt.QuestionResponse;

import java.util.List;

public interface AnswerCheckService {
    int getQuantityOfCorrectAnswers(List<QuestionResponse> questionResponses);
    List<QuestionResponseDTO> getWrongAnswers(List<QuestionResponse> questionResponses);
}