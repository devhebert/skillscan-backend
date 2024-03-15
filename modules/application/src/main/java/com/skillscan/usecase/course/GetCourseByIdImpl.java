package com.skillscan.usecase.course;

import com.skillscan.exception.course.ErrorMessages;
import com.skillscan.mapper.course.CourseMapper;
import com.skillscan.repository.CourseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetCourseByIdImpl implements GetCourseById {
    private final CourseRepository courseRepository;
    private final Logger logger = LoggerFactory.getLogger(GetCourseByIdImpl.class);

    public GetCourseByIdImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute(InputPort input) {
        try {
            if (input.id() == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            Optional<OutputPort> result = this.courseRepository.findById(input.id())
                    .map(course -> new OutputPort.Ok(CourseMapper.INSTANCE.courseToCourseDTO(course)));

            return result.orElse(new OutputPort.Error(ErrorMessages.COURSE_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error get course by id", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
