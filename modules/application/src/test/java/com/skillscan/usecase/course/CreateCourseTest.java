package com.skillscan.usecase.course;

import com.skillscan.model.course.Course;
import com.skillscan.repository.CourseRepository;
import com.skillscan.usecase.course.CreateCourse;
import com.skillscan.usecase.course.CreateCourseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCourseTest {
    @InjectMocks
    private CreateCourseImpl createCourse;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void test_execute() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");
        Course savedCourse = new Course(technologyId, name, content, keywords);
        savedCourse.setId(UUID.randomUUID());

        when(courseRepository.findByName(name)).thenReturn(Optional.empty());
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, name, content, keywords);

        // Act
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertAll(
            () -> assertTrue(outputPort instanceof CreateCourse.OutputPort.Ok),
            () -> {
                assert outputPort instanceof CreateCourse.OutputPort.Ok;
                assertEquals(savedCourse.getId(), ((CreateCourse.OutputPort.Ok) outputPort).id());
            }
        );
    }

    @Test
    public void test_execute_with_null_Input() {
        // Act
        CreateCourse.OutputPort outputPort = createCourse.execute(null);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_existing_course() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");
        when(courseRepository.findByName(name)).thenReturn(Optional.of(new Course()));

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, name, content, keywords);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_name() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, null, content, keywords);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_content() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        List<String> keywords = List.of("Java", "Programming");

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, name, null, keywords);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_technologyId() {
        // Arrange
        String name = "Java";
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(null, name, content, keywords);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_null_keywords() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        String content = "Java content";

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, name, content, null);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_empty_keywords() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String name = "Java";
        String content = "Java content";
        List<String> keywords = List.of();

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, name, content, keywords);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }

    @Test
    public void test_execute_with_empty_name() {
        // Arrange
        UUID technologyId = UUID.randomUUID();
        String name = "";
        String content = "Java content";
        List<String> keywords = List.of("Java", "Programming");

        // Act
        CreateCourse.InputPort inputPort = new CreateCourse.InputPort(technologyId, name, content, keywords);
        CreateCourse.OutputPort outputPort = createCourse.execute(inputPort);

        // Assert
        assertTrue(outputPort instanceof CreateCourse.OutputPort.Error);
    }
}
