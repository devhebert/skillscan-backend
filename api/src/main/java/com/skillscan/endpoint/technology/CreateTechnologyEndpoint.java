package com.skillscan.endpoint.technology;

import com.skillscan.endpoint.BaseTechnologyEndpoint;
import com.skillscan.model.enums.Category;
import com.skillscan.usecase.technology.CreateTechnology;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateTechnologyEndpoint extends BaseTechnologyEndpoint {
    private final CreateTechnology useCase;

    public CreateTechnologyEndpoint(CreateTechnology useCase) {
        this.useCase = useCase;
    }

    public record Request(Category category, String name) {
    }

    @Operation(summary = "Create a new technology")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreateTechnology.OutputPort result = this.useCase.execute(new CreateTechnology.InputPort(request.category(), request.name()));

        return switch (result) {
            case CreateTechnology.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateTechnology.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
