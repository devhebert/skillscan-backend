package com.skillscan.repository;

import com.skillscan.model.question.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepositoryImpl extends MongoRepository<Question, UUID>, QuestionRepository {
    default List<Question> all() {
        return findAll();
    }

    Optional<Question> findById(UUID id);
}
