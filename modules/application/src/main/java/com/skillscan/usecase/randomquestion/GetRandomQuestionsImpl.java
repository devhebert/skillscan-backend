package com.skillscan.usecase.randomquestion;

import com.skillscan.dto.question.QuestionDTO;
import com.skillscan.dto.randomquestion.RandomQuestionDTO;
import com.skillscan.exception.quizattempt.ErrorMessages;
import com.skillscan.mapper.question.QuestionMapper;
import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Question;
import com.skillscan.model.quizattempt.QuizAttempt;
import com.skillscan.repository.QuizAttemptRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetRandomQuestionsImpl implements GetRandomQuestions {
    private final MongoTemplate mongoTemplate;
    private final QuizAttemptRepository quizAttemptRepository;

    public GetRandomQuestionsImpl(MongoTemplate mongoTemplate, QuizAttemptRepository quizAttemptRepository) {
        this.mongoTemplate = mongoTemplate;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    @Transactional(readOnly = true)
    @CacheEvict(value = "randomQuestion", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            QuizAttempt lastQuizAttempt = quizAttemptRepository.findTopByUserIdAndTechnologyIdOrderByTestDateDesc(inputPort.userId(), inputPort.technologyId());
            if (lastQuizAttempt != null) {
                LocalDateTime nextAvailableDate = lastQuizAttempt.getTestDate().plusDays(15);
                if (nextAvailableDate.isAfter(LocalDateTime.now())) {
                    long daysRemaining = ChronoUnit.DAYS.between(LocalDateTime.now(), nextAvailableDate);
                    return new GetRandomQuestions.OutputPort.Error(String.format(ErrorMessages.QUIZ_ATTEMPT_TOO_SOON, daysRemaining));
                }
            }

            List<QuestionDTO> initialQuestions = getRandomQuestions(inputPort.technologyId(), Difficulty.INITIAL, 3);
            List<QuestionDTO> intermediateQuestions = getRandomQuestions(inputPort.technologyId(), Difficulty.INTERMEDIATE, 4);
            List<QuestionDTO> advancedQuestions = getRandomQuestions(inputPort.technologyId(), Difficulty.ADVANCED, 3);

            List<QuestionDTO> randomQuestionDTOs = new ArrayList<>();
            randomQuestionDTOs.addAll(initialQuestions);
            randomQuestionDTOs.addAll(intermediateQuestions);
            randomQuestionDTOs.addAll(advancedQuestions);

            Collections.shuffle(randomQuestionDTOs);

            RandomQuestionDTO randomQuestions = new RandomQuestionDTO(randomQuestionDTOs);

            return new OutputPort.Ok(Collections.singletonList(randomQuestions));

        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }

    private List<QuestionDTO> getRandomQuestions(UUID id, Difficulty difficulty, int limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("technologyId").is(id).and("difficulty").is(difficulty));
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.limit(limit);

        List<Question> questions = mongoTemplate.find(query, Question.class);

        return questions.stream()
                .map(QuestionMapper.INSTANCE::questionToQuestionDTO)
                .collect(Collectors.toList());
    }
}
