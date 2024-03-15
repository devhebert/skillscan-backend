package com.skillscan.usecase.course;

import com.skillscan.exception.course.ErrorMessages;
import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

public class CreateCourseImpl implements CreateCourse {
    private final CourseRepository courseRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateCourseImpl.class);

    public CreateCourseImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    @CacheEvict(value = "courses", allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) {
                return new OutputPort.Error(ErrorMessages.INVALID_INPUT);
            }

            if(this.courseRepository.findByName(inputPort.name()).isPresent()) {
                return new OutputPort.Error(ErrorMessages.COURSE_ALREADY_EXISTS);
            }

            OutputPort validationOutput = validateCourse(inputPort);
            if (validationOutput != null) return validationOutput;

            Course saved = this.courseRepository.save(new Course(inputPort.technologyId() ,inputPort.name(), inputPort.content(), inputPort.keywords()));
            return new OutputPort.Ok(saved.getId());
        } catch (Exception e) {
            logger.error("Error creating course", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateCourse(InputPort course) {
        if (course.name() == null || course.name().trim().isEmpty()) {
            return new OutputPort.Error("O nome não pode ser nulo ou vazio.");
        }

        if (course.content() == null || course.content().trim().isEmpty()) {
            return new OutputPort.Error("O conteudo não pode ser nulo ou vazio.");
        }

        if (course.technologyId() == null) {
            return new OutputPort.Error("The technology id cannot be null.");
        }

        if (course.keywords() == null || course.keywords().isEmpty()) {
            return new OutputPort.Error("The keywords cannot be null or empty.");
        }

        return null;
    }
}
