package com.skillscan.usecase.course;

import com.skillscan.exception.course.ErrorMessages;
import com.skillscan.repository.CourseRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteCourseImpl implements DeleteCourse {
    private final CourseRepository courseRepository;

    public DeleteCourseImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    @CacheEvict(value = "courses", allEntries = true)
    public OutputPort execute(UUID id) {
        try {
            if (id == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            if (this.courseRepository.findById(id).isEmpty()) return new OutputPort.NotFound();

            this.courseRepository.deleteById(id);
            return new OutputPort.Ok();
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }
}
