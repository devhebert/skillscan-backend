package com.skillscan.usecase.question;

import com.skillscan.dto.question.QuestionDTO;
import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetQuestionByIdTest {
    @InjectMocks
    private GetQuestionByIdImpl getQuestionById;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void test_execute() {
        // Arrange
        UUID id = UUID.randomUUID();
        GetQuestionById.InputPort input = new GetQuestionById.InputPort(id);
        Question question = new Question();
        question.setId(id);

        when(questionRepository.findById(id)).thenReturn(Optional.of(question));

        // Act
        GetQuestionById.OutputPort result = getQuestionById.execute(input);

        // Assert
        assertTrue(result instanceof GetQuestionById.OutputPort.Ok);
        GetQuestionById.OutputPort.Ok okResult = (GetQuestionById.OutputPort.Ok) result;
        assertEquals(id, okResult.question().id());
    }

    @Test
    public void test_execute_not_found() {
        // Arrange
        UUID id = UUID.randomUUID();
        GetQuestionById.InputPort input = new GetQuestionById.InputPort(id);

        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        GetQuestionById.OutputPort result = getQuestionById.execute(input);

        // Assert
        assertTrue(result instanceof GetQuestionById.OutputPort.Error);
        GetQuestionById.OutputPort.Error errorResult = (GetQuestionById.OutputPort.Error) result;
        assertEquals("Questão não encontrada.", errorResult.message());
    }

    @Test
    public void test_execute_invalid_input() {
        // Arrange
        GetQuestionById.InputPort input = new GetQuestionById.InputPort(null);

        // Act
        GetQuestionById.OutputPort result = getQuestionById.execute(input);

        // Assert
        assertTrue(result instanceof GetQuestionById.OutputPort.Error);
        GetQuestionById.OutputPort.Error errorResult = (GetQuestionById.OutputPort.Error) result;
        assertEquals("Verifique se os dados inseridos estão corretos.", errorResult.message());
    }

    @Test
    public void test_execute_exception() {
        // Arrange
        UUID id = UUID.randomUUID();
        GetQuestionById.InputPort input = new GetQuestionById.InputPort(id);

        when(questionRepository.findById(id)).thenThrow(new RuntimeException());

        // Act
        GetQuestionById.OutputPort result = getQuestionById.execute(input);

        // Assert
        assertTrue(result instanceof GetQuestionById.OutputPort.Error);
        GetQuestionById.OutputPort.Error errorResult = (GetQuestionById.OutputPort.Error) result;
        assertEquals("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", errorResult.message());
    }
}
