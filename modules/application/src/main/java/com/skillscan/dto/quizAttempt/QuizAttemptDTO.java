package com.skillscan.dto.quizAttempt;

import com.skillscan.dto.course.CourseSuggestionDTO;
import com.skillscan.dto.question.QuestionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record QuizAttemptDTO(UUID id, UUID userId, UUID technologyId, List<QuestionResponseDTO> questionResponses, Integer score, List<CourseSuggestionDTO> courseSuggestions, LocalDateTime testDate) {}
