package com.skillscan.usecase.course;

import com.skillscan.dto.course.CourseDTO;

import java.util.UUID;

public interface GetCourseById {
    public record InputPort(UUID id) {}

    public sealed interface OutputPort {
        public final record Ok(CourseDTO courseDTO) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    public OutputPort execute(InputPort input);
}
