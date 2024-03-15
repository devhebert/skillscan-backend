package com.skillscan.endpoint.question;

import com.skillscan.endpoint.BaseQuestionEndpoint;
import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Answer;
import com.skillscan.usecase.question.UpdateQuestion;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UpdateQuestionEndpoint extends BaseQuestionEndpoint {
    private final UpdateQuestion useCase;

    public UpdateQuestionEndpoint(UpdateQuestion useCase) {
        this.useCase = useCase;
    }

    public record Request(String questionText, List<Answer> answers, Difficulty difficulty, UUID technologyId) { }

    @Operation(summary = "Update a question")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> execute(@PathVariable UUID id, @RequestBody @Valid Request request) {
        UpdateQuestion.OutputPort result = this.useCase.execute(new UpdateQuestion.InputPort(id, request.questionText(), request.answers(), request.difficulty(), request.technologyId()));

        return switch (result) {
            case UpdateQuestion.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case UpdateQuestion.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
            case UpdateQuestion.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
        };
    }
}
