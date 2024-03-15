package com.skillscan.usecase.course;

import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCourseTest {
    @InjectMocks
    private UpdateCourseImpl updateCourse;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void test_execute() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");

        UpdateCourse.InputPort inputPort = new UpdateCourse.InputPort(id, technologyId, name, content, keywords);

        when(courseRepository.findById(id)).thenReturn(Optional.of(new Course(technologyId, name, content, keywords)));
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(technologyId, name, content, keywords));

        // Act
        UpdateCourse.OutputPort outputPort = updateCourse.execute(inputPort);

        // Assert
        assert outputPort instanceof UpdateCourse.OutputPort.Ok;
    }

    @Test
    public void test_execute_with_null_Input() {
        // Act
        UpdateCourse.OutputPort outputPort = updateCourse.execute(null);

        // Assert
        assert outputPort instanceof UpdateCourse.OutputPort.Error;
    }

    @Test
    public void test_execute_with_invalid_name() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID technologyId = UUID.randomUUID();
        String name = "J";
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");

        UpdateCourse.InputPort inputPort = new UpdateCourse.InputPort(id, technologyId, name, content, keywords);

        // Act
        UpdateCourse.OutputPort outputPort = updateCourse.execute(inputPort);

        // Assert
        assert outputPort instanceof UpdateCourse.OutputPort.NotFound;
    }

    @Test
    public void test_execute_with_invalid_content() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        String content = "J";
        List<String> keywords = List.of("Java", "Programming");

        UpdateCourse.InputPort inputPort = new UpdateCourse.InputPort(id, technologyId, name, content, keywords);

        // Act
        UpdateCourse.OutputPort outputPort = updateCourse.execute(inputPort);

        // Assert
        assert outputPort instanceof UpdateCourse.OutputPort.NotFound;
    }
}
