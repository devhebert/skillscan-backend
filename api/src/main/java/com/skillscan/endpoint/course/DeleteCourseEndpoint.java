package com.skillscan.endpoint.course;

import com.skillscan.endpoint.BaseCourseEndpoint;
import com.skillscan.usecase.course.DeleteCourse;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteCourseEndpoint extends BaseCourseEndpoint {
    DeleteCourse useCase;

    public DeleteCourseEndpoint(DeleteCourse useCase) {
        this.useCase = useCase;
    }

    @Operation(operationId = "delete-course", summary = "Delete a course", description = "This operation deletes a course with the given UUID")
    @DeleteMapping("/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id) {
        DeleteCourse.OutputPort result = this.useCase.execute(id);

        return switch (result) {
            case DeleteCourse.OutputPort.Ok __ -> ResponseEntity.noContent().build();
            case DeleteCourse.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
            case DeleteCourse.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
