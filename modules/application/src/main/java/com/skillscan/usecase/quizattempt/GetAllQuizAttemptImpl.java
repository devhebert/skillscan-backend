package com.skillscan.usecase.quizattempt;

import com.skillscan.mapper.quizattempt.QuizAttemptMapper;
import com.skillscan.model.quizattempt.QuizAttempt;
import com.skillscan.repository.QuizAttemptRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllQuizAttemptImpl  implements GetAllQuizAttempt {
    private final QuizAttemptRepository quizAttemptRepository;

    public GetAllQuizAttemptImpl(QuizAttemptRepository quizAttemptRepository) {
        this.quizAttemptRepository = quizAttemptRepository;

    }

    @Transactional(readOnly = true)
    public OutputPort execute() {
        try {
            List<QuizAttempt> quizAttempts = this.quizAttemptRepository.all();

            if (quizAttempts.isEmpty()) return new OutputPort.NoResults();

            return new OutputPort.Ok(quizAttempts.stream()
                    .map(QuizAttemptMapper.INSTANCE::quizAttemptToQuizAttemptDTO)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }
}
