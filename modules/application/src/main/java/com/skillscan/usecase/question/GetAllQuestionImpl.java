package com.skillscan.usecase.question;

import com.skillscan.mapper.question.QuestionMapper;
import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllQuestionImpl implements GetAllQuestion {
    private final QuestionRepository questionRepository;

    public GetAllQuestionImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute() {
        try {
            List<Question> questions = this.questionRepository.all();

            if (questions.isEmpty()) return new OutputPort.NoResults();

            return new OutputPort.Ok(questions.stream()
                    .map(QuestionMapper.INSTANCE::questionToQuestionDTO)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }
}
