package com.skillscan.endpoint.quizattempt;

import com.skillscan.endpoint.BaseQuizAttemptEndpoint;
import com.skillscan.model.quizattempt.QuestionResponse;
import com.skillscan.usecase.quizattempt.CreateQuizAttempt;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CreateQuizAttemptEndpoint extends BaseQuizAttemptEndpoint {
    private final CreateQuizAttempt useCase;

    public CreateQuizAttemptEndpoint(CreateQuizAttempt useCase) {
        this.useCase = useCase;
    }

    public record Request(UUID userId, UUID technologyId, List<QuestionResponse> questionResponses) { }

    @Operation(summary = "Create a new quiz attempt")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreateQuizAttempt.OutputPort result = this.useCase.execute(new CreateQuizAttempt.InputPort(request.userId(), request.technologyId(), request.questionResponses()));

        return switch (result) {
            case CreateQuizAttempt.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateQuizAttempt.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
