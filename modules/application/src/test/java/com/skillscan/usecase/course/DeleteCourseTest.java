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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteCourseTest {
    @InjectMocks
    private DeleteCourseImpl deleteCourse;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void test_execute() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(courseRepository.findById(id)).thenReturn(Optional.of(new Course()));
        doNothing().when(courseRepository).deleteById(id);

        // Act
        DeleteCourse.OutputPort outputPort = deleteCourse.execute(id);

        // Assert
        assertTrue(outputPort instanceof DeleteCourse.OutputPort.Ok);
    }

    @Test
    public void test_execute_with_not_found_course() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        DeleteCourse.OutputPort outputPort = deleteCourse.execute(id);

        // Assert
        assertTrue(outputPort instanceof DeleteCourse.OutputPort.NotFound);
    }

    @Test
    public void test_execute_with_error() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(courseRepository.findById(id)).thenThrow(new RuntimeException("Error"));

        // Act
        DeleteCourse.OutputPort outputPort = deleteCourse.execute(id);

        // Assert
        assertTrue(outputPort instanceof DeleteCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_id() {
        // Act
        DeleteCourse.OutputPort outputPort = deleteCourse.execute(null);

        // Assert
        assertTrue(outputPort instanceof DeleteCourse.OutputPort.Error);
    }

}
