package com.skillscan.service;

import com.skillscan.dto.course.CourseSuggestionDTO;
import com.skillscan.dto.quizAttempt.QuizResponseDTO;

import java.util.List;

public interface CustomResponseService {
    QuizResponseDTO manageResponse(Integer score, List<CourseSuggestionDTO> courseList);
}