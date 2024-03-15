package com.skillscan.endpoint.technology;

import com.skillscan.endpoint.BaseTechnologyEndpoint;
import com.skillscan.usecase.technology.GetTechnologyById;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetTechnologyByIdEndpoint extends BaseTechnologyEndpoint {
    private final GetTechnologyById useCase;

    public GetTechnologyByIdEndpoint(GetTechnologyById useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get a technology by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID id) {
        GetTechnologyById.OutputPort result = this.useCase.execute(new GetTechnologyById.InputPort(id));

        return switch (result) {
            case GetTechnologyById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetTechnologyById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
