package com.skillscan.repository;

import com.skillscan.model.tecnology.Technology;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnologyRepositoryImpl extends MongoRepository<Technology, UUID>, TechnologyRepository {
    @Cacheable(value = "technology")
    default List<Technology> all() {
        return findAll();
    }

    Optional<Technology> findById(UUID id);
}
