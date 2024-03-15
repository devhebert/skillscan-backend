package com.skillscan.usecase.quizattempt;

import com.skillscan.dto.course.CourseSuggestionDTO;
import com.skillscan.dto.question.QuestionResponseDTO;
import com.skillscan.dto.quizAttempt.QuizResponseDTO;
import com.skillscan.exception.quizattempt.ErrorMessages;
import com.skillscan.model.coursesuggestion.CourseSuggestion;
import com.skillscan.model.quizattempt.QuizAttempt;
import com.skillscan.repository.QuizAttemptRepository;
import com.skillscan.service.AnswerCheckServiceImpl;
import com.skillscan.service.CourseSuggestionServiceImpl;
import com.skillscan.service.CustomResponseServiceImpl;
import com.skillscan.usecase.technology.CreateTechnologyImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQuizAttemptImpl implements CreateQuizAttempt {
    private final AnswerCheckServiceImpl answerCheckServiceImpl;
    private final CourseSuggestionServiceImpl courseSuggestionServiceImpl;
    private final CustomResponseServiceImpl customResponseServiceImpl;
    private final QuizAttemptRepository quizAttemptRepository;
    private final Logger logger = LoggerFactory.getLogger(CreateTechnologyImpl.class);

    public CreateQuizAttemptImpl(AnswerCheckServiceImpl answerCheckServiceImpl, CourseSuggestionServiceImpl courseSuggestionServiceImpl, CustomResponseServiceImpl customResponseServiceImpl, QuizAttemptRepository quizAttemptRepository) {
        this.answerCheckServiceImpl = answerCheckServiceImpl;
        this.courseSuggestionServiceImpl = courseSuggestionServiceImpl;
        this.customResponseServiceImpl = customResponseServiceImpl;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    @Transactional
    @CacheEvict(value = "quizAttempt", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            OutputPort.Error validationError = validateInput(inputPort);
            if (validationError != null) {
                return validationError;
            }

            QuizAttempt lastQuizAttempt = quizAttemptRepository.findTopByUserIdAndTechnologyIdOrderByTestDateDesc(inputPort.userId(), inputPort.technologyId());
            if (lastQuizAttempt != null) {
                LocalDateTime nextAvailableDate = lastQuizAttempt.getTestDate().plusDays(15);
                if (nextAvailableDate.isAfter(LocalDateTime.now())) {
                    long daysRemaining = ChronoUnit.DAYS.between(LocalDateTime.now(), nextAvailableDate);
                    return new OutputPort.Error(String.format(ErrorMessages.QUIZ_ATTEMPT_TOO_SOON, daysRemaining));
                }
            }

            Integer score = this.answerCheckServiceImpl.getQuantityOfCorrectAnswers(inputPort.questionResponseList());

            List<QuestionResponseDTO> wrongAnswers = this.answerCheckServiceImpl.getWrongAnswers(inputPort.questionResponseList());

            List<CourseSuggestionDTO> courseList = new ArrayList<>();
            List<CourseSuggestion> courseSuggestions = new ArrayList<>();
            if (score <= 9) {
                courseList = this.courseSuggestionServiceImpl.suggestCourses(wrongAnswers, inputPort.technologyId());

                courseSuggestions = courseList.stream()
                        .map(courseSuggestionDTO -> new CourseSuggestion(courseSuggestionDTO.name(), courseSuggestionDTO.content()))
                        .collect(Collectors.toList());
            }

            QuizAttempt saved = this.quizAttemptRepository.save(new QuizAttempt(inputPort.userId(), inputPort.technologyId(), inputPort.questionResponseList(), score, courseSuggestions));

            QuizResponseDTO quizResponse = this.customResponseServiceImpl.manageResponse(score, courseList);

            return new OutputPort.Ok(saved.getId(), quizResponse);
        } catch (Exception e) {
            logger.error("Error creating quizattempt", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort.Error validateInput(InputPort inputPort) {
        if (inputPort == null || inputPort.userId() == null || inputPort.technologyId() == null || inputPort.questionResponseList().isEmpty()) {
            return new OutputPort.Error(ErrorMessages.INVALID_INPUT);
        }

        if (inputPort.questionResponseList().stream().anyMatch(questionResponse -> questionResponse.getQuestionId() == null || questionResponse.getAnswer() == null)) {
            return new OutputPort.Error(ErrorMessages.INVALID_QUESTION_OR_ANSWER);
        }

        if (inputPort.questionResponseList().stream().anyMatch(questionResponse -> questionResponse.getQuestionId() == null || questionResponse.getAnswer() == null || questionResponse.getAnswer().isEmpty())) {
            return new OutputPort.Error(ErrorMessages.EMPTY_QUESTION_ID);
        }

        if (inputPort.questionResponseList().stream().anyMatch(questionResponse -> questionResponse.getQuestionId() == null || questionResponse.getAnswer() == null || questionResponse.getAnswer().isEmpty())) {
            return new OutputPort.Error(ErrorMessages.EMPTY_ANSWER);
        }

        if (inputPort.questionResponseList().size() != 10) {
            return new OutputPort.Error(ErrorMessages.INVALID_QUESTION_COUNT);
        }

        return null;
    }
}
