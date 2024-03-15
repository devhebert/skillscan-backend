package com.skillscan.endpoint.quizattempt;

import com.skillscan.endpoint.BaseQuizAttemptEndpoint;
import com.skillscan.usecase.quizattempt.GetQuizAttemptById;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetQuizAttemptByIdEndpoint extends BaseQuizAttemptEndpoint {
    private final GetQuizAttemptById useCase;

    public GetQuizAttemptByIdEndpoint(GetQuizAttemptById useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get a quiz attempt by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID id) {
        GetQuizAttemptById.OutputPort result = this.useCase.execute(new GetQuizAttemptById.InputPort(id));

        return switch (result) {
            case GetQuizAttemptById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetQuizAttemptById.OutputPort.NoResults __ -> ResponseEntity.notFound().build();
            case GetQuizAttemptById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
            case GetQuizAttemptById.OutputPort.NotAuthorized __ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
