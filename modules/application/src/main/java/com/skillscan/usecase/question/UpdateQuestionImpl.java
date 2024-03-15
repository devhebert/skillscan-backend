package com.skillscan.usecase.question;

import com.skillscan.exception.question.ErrorMessages;
import com.skillscan.model.question.Answer;
import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

public class UpdateQuestionImpl implements UpdateQuestion {
    private final QuestionRepository questionRepository;

    private final Logger logger = LoggerFactory.getLogger(UpdateQuestionImpl.class);

    public UpdateQuestionImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    @CacheEvict(value = "question", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            return this.questionRepository.findById(inputPort.id())
                    .map(question -> {
                        OutputPort validationOutput = this.validateInput(inputPort);
                        if (validationOutput != null) return validationOutput;

                        this.updateFields(question, inputPort);

                        Question  updatedQuestion = this.questionRepository.save(question);

                        return new OutputPort.Ok(updatedQuestion.getId());
                    })
                    .orElse(new OutputPort.NotFound(ErrorMessages.QUESTION_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating question", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateInput(InputPort inputPort) {
        if (inputPort.questionText() != null && inputPort.questionText().length() < 10) {
            return new OutputPort.Error("A questão deve ter pelo menos 10 caracteres.");
        }

        if (inputPort.answers() == null || inputPort.answers().size() < 4) {
            return new OutputPort.Error("A questão deve ter pelo menos 4 respostas.");
        }

        boolean hasCorrectAnswer = inputPort.answers().stream().anyMatch(Answer::getIsCorrect);
        if (!hasCorrectAnswer) {
            return new OutputPort.Error("Deve haver pelo menos uma resposta correta.");
        }

        if (inputPort.difficulty() == null) {
            return new OutputPort.Error("A dificuldade da questão é obrigatória.");
        }

        if (inputPort.technologyId() == null) {
            return new OutputPort.Error("A tecnologia da questão é obrigatória.");
        }

        return null;
    }

    private void updateFields(Question question, InputPort inputPort) {
        if  (inputPort.questionText() != null) {
            question.setQuestionText(inputPort.questionText());
        }

        if (inputPort.answers() != null) {
            question.setAnswers(inputPort.answers());
        }

        if (inputPort.difficulty() != null) {
            question.setDifficulty(inputPort.difficulty());
        }

        if (inputPort.technologyId() != null) {
            question.setTechnologyId(inputPort.technologyId());
        }
    }
}
