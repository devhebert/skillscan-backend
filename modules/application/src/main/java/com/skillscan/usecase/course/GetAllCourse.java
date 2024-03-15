package com.skillscan.usecase.course;

import com.skillscan.dto.course.CourseDTO;

import java.util.List;

public interface GetAllCourse {
    sealed interface OutputPort permits OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(List<CourseDTO> list) implements OutputPort {}
        public final record NoResults() implements OutputPort {}
        public final record NotAuthorized() implements OutputPort {}
    }

    OutputPort execute();
}
