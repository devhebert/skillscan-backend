package com.skillscan.usecase.question;

import com.skillscan.exception.question.ErrorMessages;
import com.skillscan.repository.QuestionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteQuestionImpl implements DeleteQuestion {
    private final QuestionRepository questionRepository;

    public DeleteQuestionImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    @CacheEvict(value = "question", allEntries = true)
    public OutputPort execute(UUID id) {
        try {
            if (id == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            if (this.questionRepository.findById(id).isEmpty()) return new OutputPort.NotFound();

            this.questionRepository.deleteById(id);
            return new OutputPort.Ok();
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }
}
