package com.skillscan.endpoint.question;

import com.skillscan.endpoint.BaseQuestionEndpoint;
import com.skillscan.usecase.question.GetQuestionById;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetQuestionByIdEndpoint extends BaseQuestionEndpoint {
    private final GetQuestionById useCase;

    public GetQuestionByIdEndpoint(GetQuestionById useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get question by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID id) {
        GetQuestionById.OutputPort result = this.useCase.execute(new GetQuestionById.InputPort(id));

        return switch (result) {
            case GetQuestionById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetQuestionById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
