package com.skillscan.service;

import com.skillscan.dto.course.CourseSuggestionDTO;
import com.skillscan.dto.question.QuestionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CourseSuggestionService {
    List<CourseSuggestionDTO> suggestCourses(List<QuestionResponseDTO> wrongAnswers, UUID technologyId);
}