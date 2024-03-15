package com.skillscan.usecase.question;

import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteQuestionTest {
    @InjectMocks
    private DeleteQuestionImpl deleteQuestion;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void test_execute() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenReturn(Optional.of(new Question()));

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Ok.class, result.getClass());
    }

    @Test
    public void test_execute_with_null_Id() {
        // Arrange
        UUID id = null;

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
    }

    @Test
    public void test_execute_with_non_existent_id() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.NotFound.class, result.getClass());
    }

    @Test
    public void test_execute_with_exception() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException());

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
    }

    @Test
    public void test_execute_with_exception_message() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException("Error"));

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
       assertEquals("Error", ((DeleteQuestion.OutputPort.Error) result).message());
    }

    @Test
    public void test_execute_with_exception_null_message() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException());

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
       assertEquals(null, ((DeleteQuestion.OutputPort.Error) result).message());
    }

    @Test
    public void test_execute_with_exception_null() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException());

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
    }

    @Test
    public void test_execute_with_exception_message_null() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException());

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
       assertEquals(null, ((DeleteQuestion.OutputPort.Error) result).message());
    }

    @Test
    public void test_execute_with_exception_message_empty() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException(""));

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
       assertEquals("", ((DeleteQuestion.OutputPort.Error) result).message());
    }

    @Test
    public void test_execute_with_exception_message_blank() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(questionRepository.findById(id)).thenThrow(new RuntimeException(" "));

        // Act
        DeleteQuestion.OutputPort result = deleteQuestion.execute(id);

        // Assert
       assertEquals(DeleteQuestion.OutputPort.Error.class, result.getClass());
       assertEquals(" ", ((DeleteQuestion.OutputPort.Error) result).message());
    }
}
