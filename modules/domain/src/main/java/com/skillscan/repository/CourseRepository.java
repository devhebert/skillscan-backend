package com.skillscan.repository;

import com.skillscan.model.course.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {
    Optional<Course> findByName(String name);
    Course save(Course course);
    Optional<Course> findById(UUID id);
    void deleteById(UUID id);
    List<Course> all();
    List<Course> findByTechnologyId(UUID technologyId);
}
