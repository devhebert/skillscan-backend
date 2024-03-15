package com.skillscan.repository;

import com.skillscan.model.quizattempt.QuizAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizAttemptRepositoryImpl extends MongoRepository<QuizAttempt, UUID>, QuizAttemptRepository {
    default List<QuizAttempt> all() {
        return findAll();
    }

    Optional<QuizAttempt> findById(UUID id);
}
