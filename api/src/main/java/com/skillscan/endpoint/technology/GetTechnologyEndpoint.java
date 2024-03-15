package com.skillscan.endpoint.technology;

import com.skillscan.dto.technology.TechnologyDTO;
import com.skillscan.endpoint.BaseTechnologyEndpoint;
import com.skillscan.usecase.technology.GetAllTechnology;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetTechnologyEndpoint extends BaseTechnologyEndpoint {
    private final GetAllTechnology useCase;

    public GetTechnologyEndpoint(GetAllTechnology useCase) {
        this.useCase = useCase;
    }
    private ResponseEntity<?> okResponse(List<TechnologyDTO> technologyDTOList) {
        return ResponseEntity.ok(technologyDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "get-all", summary = "Get all technologies", description = "This operation retrieves all technologies from the database")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllTechnology.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllTechnology.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllTechnology.OutputPort.NoResults __ ->noResultResponse();
            case GetAllTechnology.OutputPort.NotAuthorized __ ->ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
