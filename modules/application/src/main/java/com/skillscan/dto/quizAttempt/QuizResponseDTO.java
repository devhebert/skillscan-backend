package com.skillscan.dto.quizAttempt;

import com.skillscan.dto.course.CourseSuggestionDTO;

import java.util.List;

public record QuizResponseDTO(String response, List<CourseSuggestionDTO> courses) {}