package com.skillscan.repository;

import com.skillscan.model.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepositoryImpl extends MongoRepository<Course, UUID>, CourseRepository{
    default List<Course> all() {
        return findAll();
    }

    Optional<Course> findById(UUID id);
}
