package com.skillscan.usecase.course;

import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllCourseTest {
    @InjectMocks
    private GetAllCourseImpl getAllCourse;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void test_execute() {
        // Arrange
        when(courseRepository.all()).thenReturn(List.of(new Course()));

        // Act
        GetAllCourse.OutputPort outputPort = getAllCourse.execute();

        // Assert
        assert outputPort instanceof GetAllCourse.OutputPort.Ok;
    }

    @Test
    public void test_execute_no_results() {
        // Arrange
        when(courseRepository.all()).thenReturn(List.of());

        // Act
        GetAllCourse.OutputPort outputPort = getAllCourse.execute();

        // Assert
        assert outputPort instanceof GetAllCourse.OutputPort.NoResults;
    }
}
