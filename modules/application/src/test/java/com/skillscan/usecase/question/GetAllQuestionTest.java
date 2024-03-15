package com.skillscan.usecase.question;

import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllQuestionTest {
    @InjectMocks
    private GetAllQuestionImpl getAllQuestion;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void test_execute() {
        // Arrange
        when(questionRepository.all()).thenReturn(List.of(new Question()));

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.Ok.class, result.getClass());
    }

    @Test
    public void test_execute_with_no_results() {
        // Arrange
        when(questionRepository.all()).thenReturn(List.of());

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NoResults.class, result.getClass());
    }

    @Test
    public void test_execute_with_exception() {
        // Arrange
        when(questionRepository.all()).thenThrow(new RuntimeException());

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NotAuthorized.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository() {
        // Arrange
        getAllQuestion = new GetAllQuestionImpl(null);

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NotAuthorized.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository_all() {
        // Arrange
        when(questionRepository.all()).thenReturn(null);

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NotAuthorized.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository_all_and_exception() {
        // Arrange
        when(questionRepository.all()).thenThrow(new RuntimeException());

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NotAuthorized.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository_all_and_no_results() {
        // Arrange
        when(questionRepository.all()).thenReturn(List.of());

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NoResults.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository_all_and_no_results_and_exception() {
        // Arrange
        when(questionRepository.all()).thenReturn(List.of());

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NoResults.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository_all_and_no_results_and_null_exception() {
        // Arrange
        when(questionRepository.all()).thenReturn(List.of());

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NoResults.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_repository_all_and_no_results_and_null_exception_and_null_repository() {
        // Arrange
        getAllQuestion = new GetAllQuestionImpl(null);

        // Act
        GetAllQuestion.OutputPort result = getAllQuestion.execute();

        // Assert
        assertEquals(GetAllQuestion.OutputPort.NotAuthorized.class, result.getClass());
    }
}
