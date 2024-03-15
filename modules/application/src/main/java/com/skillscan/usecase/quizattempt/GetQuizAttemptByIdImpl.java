package com.skillscan.usecase.quizattempt;

import com.skillscan.exception.quizattempt.ErrorMessages;
import com.skillscan.mapper.quizattempt.QuizAttemptMapper;
import com.skillscan.repository.QuizAttemptRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetQuizAttemptByIdImpl implements  GetQuizAttemptById {
    private final QuizAttemptRepository quizAttemptRepository;
    private final Logger logger = LoggerFactory.getLogger(GetQuizAttemptByIdImpl.class);

    public GetQuizAttemptByIdImpl(QuizAttemptRepository quizAttemptRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute(InputPort input) {
        try {
            if (input.id() == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            Optional<OutputPort> result = this.quizAttemptRepository.findById(input.id())
                    .map(quizAttempt -> new OutputPort.Ok(QuizAttemptMapper.INSTANCE.quizAttemptToQuizAttemptDTO(quizAttempt)));

            return result.orElse(new OutputPort.Error(ErrorMessages.QUIZ_ATTEMPT_NOT_FOUND));
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }
}
