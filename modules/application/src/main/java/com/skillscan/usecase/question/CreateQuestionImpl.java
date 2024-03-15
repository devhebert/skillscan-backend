package com.skillscan.usecase.question;

import com.skillscan.exception.question.ErrorMessages;
import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

public class CreateQuestionImpl implements CreateQuestion {
    private final QuestionRepository questionRepository;

    private final Logger logger = LoggerFactory.getLogger(CreateQuestionImpl.class);

    public CreateQuestionImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    @CacheEvict(value = "question", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) {
                return new OutputPort.Error(ErrorMessages.INVALID_INPUT);
            }

            if (this.questionRepository.findByQuestionText(inputPort.questionText()).isPresent()) {
                return new OutputPort.Error(ErrorMessages.QUESTION_ALREADY_EXISTS);
            }

            OutputPort validationOutput = validateQuestion(inputPort);
            if (validationOutput != null) return validationOutput;

            Question saved = this.questionRepository.save(new Question(inputPort.questionText(), inputPort.answerList(), inputPort.difficulty(), inputPort.technologyId()));

            return new OutputPort.Ok(saved.getId());
        } catch (Exception e) {
            logger.error("Error creating question", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateQuestion(InputPort inputPort) {
        if (inputPort.questionText() == null || inputPort.questionText().trim().isEmpty()) {
            return new OutputPort.Error("The question text cannot be null or empty.");
        }

        if (inputPort.answerList() == null || inputPort.answerList().isEmpty()) {
            return new OutputPort.Error("The answer list cannot be null or empty.");
        }

        if (inputPort.answerList().stream().filter(answer -> Boolean.TRUE.equals(answer.getIsCorrect())).count() != 1) {
            return new OutputPort.Error("There must be exactly one correct answer.");
        }

        if (inputPort.difficulty() == null) {
            return new OutputPort.Error("The difficulty cannot be null.");
        }

        if (inputPort.technologyId() == null) {
            return new OutputPort.Error("The technology id cannot be null.");
        }

        return null;
    }
}
