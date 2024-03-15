package com.skillscan.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "QuizAttempt", description = "The QuizAttempt API")
@RestController
@RequestMapping("/api/v1/quiz-attempt")
public abstract class BaseQuizAttemptEndpoint {}
