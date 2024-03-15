package com.skillscan.endpoint.course;

import com.skillscan.endpoint.BaseCourseEndpoint;
import com.skillscan.usecase.course.UpdateCourse;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UpdateCourseEndpoint extends BaseCourseEndpoint {
    private final UpdateCourse useCase;

    public UpdateCourseEndpoint(UpdateCourse useCase) {
        this.useCase = useCase;
    }

    public record Request(UUID technologyId, String name, String content, List<String> keywords) { }

    @Operation(summary = "Update a course course")
    @PutMapping(value = "/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id, @RequestBody @Valid Request request) {
        UpdateCourse.OutputPort result = this.useCase.execute(new UpdateCourse.InputPort(id, request.technologyId() ,request.name(), request.content(), request.keywords()));

        return switch (result) {
            case UpdateCourse.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case UpdateCourse.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
            case UpdateCourse.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
        };
    }
}
