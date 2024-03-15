package com.skillscan.dto.question;


import java.util.UUID;

public record QuestionResponseDTO(UUID questionId, String answer) {}
