package com.skillscan.endpoint.question;

import com.skillscan.dto.question.QuestionDTO;
import com.skillscan.endpoint.BaseQuestionEndpoint;
import com.skillscan.usecase.question.GetAllQuestion;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetQuestionEndpoint extends BaseQuestionEndpoint {
    private final GetAllQuestion useCase;

    public GetQuestionEndpoint(GetAllQuestion useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<QuestionDTO> questionDTOList) {
        return ResponseEntity.ok(questionDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "get-all", summary = "Get all questions", description = "This operation retrieves all questions from the database")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllQuestion.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllQuestion.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllQuestion.OutputPort.NoResults __ -> noResultResponse();
            case GetAllQuestion.OutputPort.NotAuthorized __ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
