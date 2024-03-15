package com.skillscan.endpoint.course;

import com.skillscan.endpoint.BaseCourseEndpoint;
import com.skillscan.usecase.course.GetCourseById;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetCourseByIdEndpoint extends BaseCourseEndpoint {
    private final GetCourseById useCase;

    public GetCourseByIdEndpoint(GetCourseById useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get course by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID id) {
        GetCourseById.OutputPort result = this.useCase.execute(new GetCourseById.InputPort(id));

        return switch (result) {
            case GetCourseById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetCourseById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
