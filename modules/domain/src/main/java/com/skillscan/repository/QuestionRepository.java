package com.skillscan.repository;

import com.skillscan.model.question.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository {
    Question save(Question question);
    List<Question> all();
    Optional<Question> findById(UUID id);
    void deleteById(UUID id);
    Optional<Object> findByQuestionText(String questionText);
}
