package com.skillscan.dto.randomquestion;

import com.skillscan.dto.question.QuestionDTO;

import java.util.List;

public record RandomQuestionDTO(List<QuestionDTO> questions) {}
