package com.skillscan.repository;

import com.skillscan.model.tecnology.Technology;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface TechnologyRepository {
    Optional<Technology> findByName(String name);
    Technology save(Technology technology);
    List<Technology> all();
    Optional<Technology> findById(UUID id);
    void deleteById(UUID id);
}
