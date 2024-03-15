package com.skillscan.usecase.quizattempt;

import com.skillscan.repository.QuizAttemptRepository;
import com.skillscan.service.AnswerCheckServiceImpl;
import com.skillscan.service.CourseSuggestionServiceImpl;
import com.skillscan.service.CustomResponseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class CreateQuizAttemptTest {
    @InjectMocks
    private CreateQuizAttemptImpl createQuizAttempt;

    @Mock
    private AnswerCheckServiceImpl answerCheckServiceImpl;

    @Mock
    private CourseSuggestionServiceImpl courseSuggestionServiceImpl;

    @Mock
    private CustomResponseServiceImpl customResponseServiceImpl;

    @Mock
    private QuizAttemptRepository quizAttemptRepository;

    @Test
    public void test_execute() {

    }


}
