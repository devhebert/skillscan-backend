package com.skillscan.usecase.course;

import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCourseByIdTest {
    @InjectMocks
    private GetCourseByIdImpl getCourseById;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void test_execute() {
        // Assert
        UUID courseId = UUID.randomUUID();

        GetCourseById.InputPort inputPort = new GetCourseById.InputPort(courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));

        // Act
        GetCourseById.OutputPort outputPort = getCourseById.execute(inputPort);

        // Assert
        assert outputPort instanceof GetCourseById.OutputPort.Ok;
    }

    @Test
    public void test_execute_with_null_Input() {
        // Arrange
        GetCourseById.InputPort inputPort = new GetCourseById.InputPort(null);

        // Act
        GetCourseById.OutputPort outputPort = getCourseById.execute(inputPort);

        // Assert
        assert outputPort instanceof GetCourseById.OutputPort.Error;
    }

    @Test
    public void test_execute_with_null_course() {
        // Arrange
        UUID courseId = UUID.randomUUID();

        GetCourseById.InputPort inputPort = new GetCourseById.InputPort(courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        GetCourseById.OutputPort outputPort = getCourseById.execute(inputPort);

        // Assert
        assert outputPort instanceof GetCourseById.OutputPort.Error;
    }
}
