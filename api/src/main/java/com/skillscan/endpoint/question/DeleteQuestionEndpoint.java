package com.skillscan.endpoint.question;

import com.skillscan.endpoint.BaseQuestionEndpoint;
import com.skillscan.usecase.question.DeleteQuestion;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteQuestionEndpoint extends BaseQuestionEndpoint {
    private final DeleteQuestion useCase;

    public DeleteQuestionEndpoint(DeleteQuestion useCase) {
        this.useCase = useCase;
    }

    @Operation(operationId = "delete-question", summary = "Delete a question", description = "This operation deletes a question with the given UUID")
    @DeleteMapping("/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id) {
        DeleteQuestion.OutputPort result = this.useCase.execute(id);

        return switch (result) {
            case DeleteQuestion.OutputPort.Ok __ -> ResponseEntity.noContent().build();
            case DeleteQuestion.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
            case DeleteQuestion.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
