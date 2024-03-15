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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionTest {
    @InjectMocks
    private CreateQuestionImpl createQuestion;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void test_execute() {
        // Arrange
        String questionText = "What is the capital of France?";
        Answer answerOne = new Answer("A", true);
        Answer answerTwo = new Answer("B", false);
        Answer answerTree = new Answer("C", false);
        Answer answerFour = new Answer("D", false);
        List<Answer> answerList = List.of(answerOne, answerTwo, answerTree, answerFour);
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(questionText, answerList, difficulty, technologyId);

        when(questionRepository.findByQuestionText(questionText)).thenReturn(Optional.empty());
        when(questionRepository.save(any(Question.class))).thenReturn(new Question(questionText, answerList, difficulty, technologyId));

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Ok);
    }

    @Test
    public void test_execute_with_null_Input() {
        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(null);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }

    @Test
    public void test_execute_with_existing_question() {
        // Arrange
        String questionText = "What is the capital of France?";
        Answer answerOne = new Answer("A", true);
        Answer answerTwo = new Answer("B", false);
        Answer answerTree = new Answer("C", false);
        Answer answerFour = new Answer("D", false);
        List<Answer> answerList = List.of(answerOne, answerTwo, answerTree, answerFour);
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(questionText, answerList, difficulty, technologyId);

        when(questionRepository.findByQuestionText(questionText)).thenReturn(Optional.of(new Question(questionText, answerList, difficulty, technologyId)));

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_questionText() {
        // Arrange
        Answer answerOne = new Answer("A", true);
        Answer answerTwo = new Answer("B", false);
        Answer answerTree = new Answer("C", false);
        Answer answerFour = new Answer("D", false);
        List<Answer> answerList = List.of(answerOne, answerTwo, answerTree, answerFour);
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(null, answerList, difficulty, technologyId);

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_answerList() {
        // Arrange
        String questionText = "What is the capital of France?";
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(questionText, null, difficulty, technologyId);

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_difficulty() {
        // Arrange
        String questionText = "What is the capital of France?";
        Answer answerOne = new Answer("A", true);
        Answer answerTwo = new Answer("B", false);
        Answer answerTree = new Answer("C", false);
        Answer answerFour = new Answer("D", false);
        List<Answer> answerList = List.of(answerOne, answerTwo, answerTree, answerFour);
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(questionText, answerList, null, technologyId);

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }

    @Test
    public void test_execute_with_answer_list_have_no_correct_answer() {
        // Arrange
        String questionText = "What is the capital of France?";
        Answer answerOne = new Answer("A", false);
        Answer answerTwo = new Answer("B", false);
        Answer answerTree = new Answer("C", false);
        Answer answerFour = new Answer("D", false);
        List<Answer> answerList = List.of(answerOne, answerTwo, answerTree, answerFour);
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(questionText, answerList, difficulty, technologyId);

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }

    @Test
    public void test_execute_with_answer_list_have_many_correct_answer() {
        // Arrange
        String questionText = "What is the capital of France?";
        Answer answerOne = new Answer("A", true);
        Answer answerTwo = new Answer("B", true);
        Answer answerTree = new Answer("C", true);
        Answer answerFour = new Answer("D", true);
        List<Answer> answerList = List.of(answerOne, answerTwo, answerTree, answerFour);
        Difficulty difficulty = Difficulty.INITIAL;
        UUID technologyId = UUID.randomUUID();

        CreateQuestion.InputPort inputPort = new CreateQuestion.InputPort(questionText, answerList, difficulty, technologyId);

        // Act
        CreateQuestion.OutputPort outputPort = createQuestion.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateQuestion.OutputPort.Error);
    }
}
