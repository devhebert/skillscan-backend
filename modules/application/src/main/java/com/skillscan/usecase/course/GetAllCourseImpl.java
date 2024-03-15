package com.skillscan.usecase.course;

import com.skillscan.mapper.course.CourseMapper;
import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllCourseImpl implements GetAllCourse {
    private final CourseRepository courseRepository;

    public GetAllCourseImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute() {
        try {
            List<Course> courses = this.courseRepository.all();

            if (courses.isEmpty()) return new OutputPort.NoResults();

            return new OutputPort.Ok(courses.stream()
                    .map(CourseMapper.INSTANCE::courseToCourseDTO)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }
}
