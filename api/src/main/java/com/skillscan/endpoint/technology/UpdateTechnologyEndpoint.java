package com.skillscan.endpoint.technology;

import com.skillscan.endpoint.BaseTechnologyEndpoint;
import com.skillscan.model.enums.Category;
import com.skillscan.usecase.technology.UpdateTechnology;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UpdateTechnologyEndpoint extends BaseTechnologyEndpoint {
    private final UpdateTechnology useCase;

    public UpdateTechnologyEndpoint(UpdateTechnology useCase) {
        this.useCase = useCase;
    }

    public record Request(Category category, String name) { }

    @Operation(summary = "Update a technology")
   @PutMapping(value = "/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id, @RequestBody @Valid Request request) {
        UpdateTechnology.OutputPort result = this.useCase.execute(new UpdateTechnology.InputPort(id, request.category() ,request.name()));

        return switch (result) {
            case UpdateTechnology.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case UpdateTechnology.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
            case UpdateTechnology.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
        };
    }
}
