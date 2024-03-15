package com.skillscan.repository;

import com.skillscan.model.quizattempt.QuizAttempt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizAttemptRepository {
    QuizAttempt save(QuizAttempt quizAttempt);
    List<QuizAttempt> all();
    Optional<QuizAttempt> findById(UUID id);
    void deleteById(UUID id);
    QuizAttempt findTopByUserIdAndTechnologyIdOrderByTestDateDesc(UUID uuid, UUID uuid1);
    List<QuizAttempt> findByUserId(UUID uuid);
    List<QuizAttempt> findByUserIdAndTechnologyIdOrderByTestDateDesc(UUID id, UUID id1);
}
