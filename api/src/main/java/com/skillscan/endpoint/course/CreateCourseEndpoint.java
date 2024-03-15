package com.skillscan.endpoint.course;

import com.skillscan.endpoint.BaseCourseEndpoint;
import com.skillscan.usecase.course.CreateCourse;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CreateCourseEndpoint extends BaseCourseEndpoint {
    private final CreateCourse useCase;

    public CreateCourseEndpoint(CreateCourse useCase) {
        this.useCase = useCase;
    }

    public record Request(UUID technologyId, String name, String content, List<String> keywords) {
    }

    @Operation(summary = "Create a course")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreateCourse.OutputPort result = this.useCase.execute(new CreateCourse.InputPort(request.technologyId(), request.name(), request.content(), request.keywords()));

        return switch (result) {
            case CreateCourse.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateCourse.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}