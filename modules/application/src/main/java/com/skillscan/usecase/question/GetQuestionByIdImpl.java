package com.skillscan.usecase.question;

import com.skillscan.exception.question.ErrorMessages;
import com.skillscan.mapper.question.QuestionMapper;
import com.skillscan.repository.QuestionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetQuestionByIdImpl implements GetQuestionById {
    private final QuestionRepository questionRepository;
    private final Logger logger = LoggerFactory.getLogger(GetQuestionByIdImpl.class);

    public GetQuestionByIdImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute(InputPort input) {
        try {
            if (input.id() == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            Optional<OutputPort> result = this.questionRepository.findById(input.id())
                    .map(question -> new OutputPort.Ok(QuestionMapper.INSTANCE.questionToQuestionDTO(question)));

            return result.orElse(new OutputPort.Error(ErrorMessages.QUESTION_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error get question by id", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
