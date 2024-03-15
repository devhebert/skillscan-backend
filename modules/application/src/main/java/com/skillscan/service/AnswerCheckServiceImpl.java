package com.skillscan.service;

import com.skillscan.dto.question.QuestionResponseDTO;
import com.skillscan.model.question.Question;
import com.skillscan.model.quizattempt.QuestionResponse;
import com.skillscan.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerCheckServiceImpl implements AnswerCheckService {
    private final QuestionRepository questionRepository;

    public AnswerCheckServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public int getQuantityOfCorrectAnswers(List<QuestionResponse> questionResponses) {
        if (questionResponses == null || questionResponses.isEmpty()) throw new IllegalArgumentException("QuestionResponses não pode ser nulo ou vazio");

        int correctAnswers = 0;
        for (QuestionResponse questionResponse : questionResponses) {
            Optional<Question> question = this.questionRepository.findById(questionResponse.getQuestionId());
            if (question.isPresent() && question.get().getAnswers().stream()
                    .anyMatch(answer -> answer.getAnswerText().equals(questionResponse.getAnswer()) && answer.getIsCorrect())) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public List<QuestionResponseDTO> getWrongAnswers(List<QuestionResponse> questionResponses) {
        if (questionResponses == null || questionResponses.isEmpty()) throw new IllegalArgumentException("QuestionResponses não pode ser nulo ou vazio");

        return questionResponses.stream()
                .filter(questionResponse -> {
                    Optional<Question> question = this.questionRepository.findById(questionResponse.getQuestionId());
                    return question.isEmpty() || question.get().getAnswers().stream()
                            .noneMatch(answer -> answer.getAnswerText().equals(questionResponse.getAnswer()) && answer.getIsCorrect());
                })
                .map(questionResponse -> new QuestionResponseDTO(questionResponse.getQuestionId(), questionResponse.getAnswer()))
                .collect(Collectors.toList());
    }
}

