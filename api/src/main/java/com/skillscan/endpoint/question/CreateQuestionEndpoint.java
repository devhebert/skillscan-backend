package com.skillscan.endpoint.question;

import com.skillscan.endpoint.BaseQuestionEndpoint;
import com.skillscan.model.enums.Difficulty;
import com.skillscan.model.question.Answer;
import com.skillscan.usecase.question.CreateQuestion;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CreateQuestionEndpoint extends BaseQuestionEndpoint {
    private final CreateQuestion useCase;

    public CreateQuestionEndpoint(CreateQuestion useCase) {
        this.useCase = useCase;
    }

    public record Request(String questionText, List<Answer> answers, Difficulty difficulty, UUID technologyId) { }

    @Operation(summary = "Create a new question")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreateQuestion.OutputPort result = this.useCase.execute(new CreateQuestion.InputPort(request.questionText(), request.answers(), request.difficulty(), request.technologyId()));

        return switch (result) {
            case CreateQuestion.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateQuestion.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
