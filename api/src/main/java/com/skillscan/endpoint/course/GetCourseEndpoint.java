package com.skillscan.endpoint.course;

import com.skillscan.dto.course.CourseDTO;
import com.skillscan.endpoint.BaseCourseEndpoint;
import com.skillscan.usecase.course.GetAllCourse;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCourseEndpoint extends BaseCourseEndpoint {
    private final GetAllCourse useCase;

    public GetCourseEndpoint(GetAllCourse useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<CourseDTO> courseDTOList) {
        return ResponseEntity.ok(courseDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "get-all", summary = "Get all courses", description = "This operation retrieves all courses from the database")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllCourse.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllCourse.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllCourse.OutputPort.NoResults __ -> noResultResponse();
            case GetAllCourse.OutputPort.NotAuthorized __ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
