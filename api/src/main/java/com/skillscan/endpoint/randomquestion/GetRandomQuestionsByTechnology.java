package com.skillscan.endpoint.randomquestion;

import com.skillscan.dto.randomquestion.RandomQuestionDTO;
import com.skillscan.endpoint.BaseRandomQuestionEndpoint;
import com.skillscan.usecase.randomquestion.GetRandomQuestions;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GetRandomQuestionsByTechnology extends BaseRandomQuestionEndpoint {
    public final GetRandomQuestions useCase;

    public GetRandomQuestionsByTechnology(GetRandomQuestions useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<RandomQuestionDTO> randomQuestionDTOList) {
        return ResponseEntity.ok(randomQuestionDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    public record Request(UUID userId, UUID technologyId) { }

    @Operation(operationId = "get-by-technology", summary = "Get random questions by technology", description = "This operation retrieves random questions by technology from the database")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID technologyId, @RequestParam UUID userId) {
        GetRandomQuestions.OutputPort result = this.useCase.execute(new GetRandomQuestions.InputPort(userId, technologyId));

        return switch (result) {
            case GetRandomQuestions.OutputPort.Ok ok -> okResponse(ok.list());
            case GetRandomQuestions.OutputPort.NoResults __ -> noResultResponse();
            case GetRandomQuestions.OutputPort.NotAuthorized __ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            case GetRandomQuestions.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
