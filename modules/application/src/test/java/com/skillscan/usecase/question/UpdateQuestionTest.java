package com.skillscan.usecase.question;

import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Answer;
import com.skillscan.model.question.Question;
import com.skillscan.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateQuestionTest {
    @InjectMocks
    private UpdateQuestionImpl updateQuestion;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void test_execute() {
        // Arrage
        UUID id = UUID.randomUUID();
        String questionText = "Qual a cor do cavalo branco de Napoleão?";
        List<Answer> answers = List.of(
                new Answer("Branco", true),
                new Answer("Preto", false),
                new Answer("Vermelho", false),
                new Answer("Verde", false));
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, questionText, answers, difficulty, technologyId);

        when(questionRepository.findById(id)).thenReturn(Optional.of(new Question()));
        when(questionRepository.save(any(Question.class))).thenReturn(new Question());

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.Ok);
        UpdateQuestion.OutputPort.Ok okResult = (UpdateQuestion.OutputPort.Ok) result;
    }

    @Test
    public void test_execute_not_found() {
        // Arrange
        UUID id = UUID.randomUUID();
        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, "Qual a cor do cavalo branco de Napoleão?", List.of(
                new Answer("Branco", true),
                new Answer("Preto", false),
                new Answer("Vermelho", false),
                new Answer("Verde", false)), Difficulty.INITIAL, UUID.randomUUID());

        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.NotFound);
        UpdateQuestion.OutputPort.NotFound notFoundResult = (UpdateQuestion.OutputPort.NotFound) result;
        assertEquals("Questão não encontrada.", notFoundResult.message());
    }

    @Test
    public void test_execute_exception() {
        // Arrange
        UUID id = UUID.randomUUID();
        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, "Qual a cor do cavalo branco de Napoleão?", List.of(
                new Answer("Branco", true),
                new Answer("Preto", false),
                new Answer("Vermelho", false),
                new Answer("Verde", false)), Difficulty.INITIAL, UUID.randomUUID());

        when(questionRepository.findById(id)).thenThrow(new RuntimeException());

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.Error);
        UpdateQuestion.OutputPort.Error errorResult = (UpdateQuestion.OutputPort.Error) result;
        assertEquals("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", errorResult.message());
    }

    @Test
    public void test_execute_invalid_answers() {
        // Arrange
        UUID id = UUID.randomUUID();
        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, "Qual a cor do cavalo branco de Napoleão?", List.of(
                new Answer("Branco", true),
                new Answer("Preto", false)), Difficulty.INITIAL, UUID.randomUUID());

        when(questionRepository.findById(id)).thenReturn(Optional.of(new Question()));

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.Error);
        UpdateQuestion.OutputPort.Error errorResult = (UpdateQuestion.OutputPort.Error) result;
        assertEquals("A questão deve ter pelo menos 4 respostas.", errorResult.message());
    }

    @Test
    public void test_execute_invalid_correct_answer() {
        // Arrange
        UUID id = UUID.randomUUID();
        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, "Qual a cor do cavalo branco de Napoleão?", List.of(
                new Answer("Branco", false),
                new Answer("Preto", false),
                new Answer("Vermelho", false),
                new Answer("Verde", false)), Difficulty.INITIAL, UUID.randomUUID());

        when(questionRepository.findById(id)).thenReturn(Optional.of(new Question()));

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.Error);
        UpdateQuestion.OutputPort.Error errorResult = (UpdateQuestion.OutputPort.Error) result;
        assertEquals("Deve haver pelo menos uma resposta correta.", errorResult.message());
    }

    @Test
    public void test_execute_invalid_difficulty() {
        // Arrange
        UUID id = UUID.randomUUID();
        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, "Qual a cor do cavalo branco de Napoleão?", List.of(
                new Answer("Branco", true),
                new Answer("Preto", false),
                new Answer("Vermelho", false),
                new Answer("Verde", false)), null, UUID.randomUUID());

        when(questionRepository.findById(id)).thenReturn(Optional.of(new Question()));

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.Error);
        UpdateQuestion.OutputPort.Error errorResult = (UpdateQuestion.OutputPort.Error) result;
        assertEquals("A dificuldade da questão é obrigatória.", errorResult.message());
    }

    @Test
    public void test_execute_invalid_technology_id() {
        // Arrange
        UUID id = UUID.randomUUID();
        UpdateQuestion.InputPort input = new UpdateQuestion.InputPort(id, "Qual a cor do cavalo branco de Napoleão?", List.of(
                new Answer("Branco", true),
                new Answer("Preto", false),
                new Answer("Vermelho", false),
                new Answer("Verde", false)), Difficulty.INITIAL, null);

        when(questionRepository.findById(id)).thenReturn(Optional.of(new Question()));

        // Act
        UpdateQuestion.OutputPort result = updateQuestion.execute(input);

        // Assert
        assertTrue(result instanceof UpdateQuestion.OutputPort.Error);
        UpdateQuestion.OutputPort.Error errorResult = (UpdateQuestion.OutputPort.Error) result;
        assertEquals("A tecnologia da questão é obrigatória.", errorResult.message());
    }
}
