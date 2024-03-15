package com.skillscan.dto.question;

import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Answer;

import java.util.List;
import java.util.UUID;

public record QuestionDTO(UUID id, String questionText, List<Answer> answers, Difficulty difficulty, UUID technologyId) {}
