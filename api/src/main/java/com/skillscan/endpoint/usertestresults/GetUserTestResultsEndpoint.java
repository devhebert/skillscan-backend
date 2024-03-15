package com.skillscan.endpoint.usertestresults;

import com.skillscan.dto.usertestresults.UserTestResultsDTO;
import com.skillscan.endpoint.BaseUserTestResultsEndpoint;
import com.skillscan.usecase.usertestresults.GetUserTestResults;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUserTestResultsEndpoint extends BaseUserTestResultsEndpoint {
    private final GetUserTestResults usecase;

    public GetUserTestResultsEndpoint(GetUserTestResults usecase) {
        this.usecase = usecase;
    }

    private ResponseEntity<?> okResponse(List<UserTestResultsDTO> userTestResults) {
        return ResponseEntity.ok(userTestResults);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user test results")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetUserTestResults.OutputPort result = this.usecase.execute();

        return switch (result) {
            case GetUserTestResults.OutputPort.Ok ok -> okResponse(ok.list());
            case GetUserTestResults.OutputPort.NoResults __ -> noResultResponse();
            case GetUserTestResults.OutputPort.NotAuthorized __ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
