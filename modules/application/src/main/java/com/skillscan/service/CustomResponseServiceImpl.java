package com.skillscan.service;

import com.skillscan.dto.course.CourseSuggestionDTO;
import com.skillscan.dto.quizAttempt.QuizResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomResponseServiceImpl implements CustomResponseService {
    private static final Integer NUMBER_OF_QUESTIONS = 10;

    public QuizResponseDTO manageResponse(Integer score, List<CourseSuggestionDTO> courseList) {
        if (score < 5) return new QuizResponseDTO(badResponse(score, courseList), courseList);

        if (score <= 9) return new QuizResponseDTO(mediumResponse(score, courseList), courseList);

        return new QuizResponseDTO(goodResponse(score), courseList);
    }

    private String badResponse(Integer score, List<CourseSuggestionDTO> courseList) {
        return generateResponse(score, "Que pena, você acertou %d de %d questões \uD83D\uDE15.", courseList);
    }

    private String mediumResponse(Integer score, List<CourseSuggestionDTO> courseList) {
        return generateResponse(score, "Que legal, você acertou %d de %d questões \uD83D\uDE00.", courseList);
    }

    private String goodResponse(Integer score) {
        return String.format("Parabéns, você acertou %d de %d questões. Continue assim! \uD83D\uDE0E.", score, NUMBER_OF_QUESTIONS);
    }

    private String generateResponse(Integer score, String message, List<CourseSuggestionDTO> courseList) {
        String response = String.format(message, score, NUMBER_OF_QUESTIONS);

        if (courseList.isEmpty()) {
            response += " No momento ainda não temos algum curso para te indicar.";
        } else if (courseList.size() == 1) {
            response += " Aqui está um curso que pode te ajudar a melhorar:";
        } else {
            response += " Aqui está uma lista de cursos que podem te ajudar a melhorar:";
        }

        return response;
    }
}