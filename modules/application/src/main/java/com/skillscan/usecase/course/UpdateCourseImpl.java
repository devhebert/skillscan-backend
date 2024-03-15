package com.skillscan.usecase.course;

import com.skillscan.exception.course.ErrorMessages;
import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

public class UpdateCourseImpl implements UpdateCourse {
    private final CourseRepository courseRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateCourseImpl.class);

    public UpdateCourseImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    @CacheEvict(value = "courses",allEntries = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            return this.courseRepository.findById(inputPort.id())
                    .map(course -> {
                        OutputPort validationOutput = this.validateInput(inputPort);
                        if (validationOutput != null) return validationOutput;

                        this.updateFields(course, inputPort);

                        Course updatedCourse = this.courseRepository.save(course);

                        return new OutputPort.Ok(updatedCourse.getId());
                    })
                    .orElse(new OutputPort.NotFound(ErrorMessages.COURSE_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating course", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }

    private OutputPort validateInput(InputPort inputPort) {
        if (inputPort.name() != null && inputPort.name().length() < 2) {
            return new OutputPort.Error("O nome do curso deve ter pelo menos 2 caracteres");
        }

        if (inputPort.content() != null && inputPort.content().length() < 2) {
            return new OutputPort.Error("O conteúdo do curso deve ter pelo menos 2 caracteres");
        }

        if (inputPort.technologyId() == null) {
            return new OutputPort.Error("O id da tecnologia não pode ser nulo");
        }

        if (inputPort.keywords() == null || inputPort.keywords().isEmpty()) {
            return new OutputPort.Error("As palavras-chave não podem ser nulas ou vazias");
        }

        return null;
    }

    private void updateFields(Course course, InputPort inputPort) {
        if (inputPort.name() != null) {
            course.setName(inputPort.name());
        }

        if (inputPort.content() != null) {
            course.setContent(inputPort.content());
        }

        if (inputPort.technologyId() != null) {
            course.setTechnologyId(inputPort.technologyId());
        }

        if (inputPort.keywords() != null) {
            course.setKeywords(inputPort.keywords());
        }
    }
}
