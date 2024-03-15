package com.skillscan.endpoint.quizattempt;

import com.skillscan.dto.quizAttempt.QuizAttemptDTO;
import com.skillscan.endpoint.BaseQuizAttemptEndpoint;
import com.skillscan.usecase.quizattempt.GetAllQuizAttempt;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetQuizAttemptEndpoint extends BaseQuizAttemptEndpoint {
    private final GetAllQuizAttempt useCase;

    public GetQuizAttemptEndpoint(GetAllQuizAttempt useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<QuizAttemptDTO> quizAttemptDTO) {
        return ResponseEntity.ok(quizAttemptDTO);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "get-all", summary = "Get all quiz attempts", description = "This operation retrieves all quiz attempts from the database")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllQuizAttempt.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllQuizAttempt.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllQuizAttempt.OutputPort.NoResults __ ->noResultResponse();
            case GetAllQuizAttempt.OutputPort.NotAuthorized __ ->ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
