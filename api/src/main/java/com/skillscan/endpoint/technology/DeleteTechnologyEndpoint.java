package com.skillscan.endpoint.technology;

import com.skillscan.endpoint.BaseTechnologyEndpoint;
import com.skillscan.usecase.technology.DeleteTechnology;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteTechnologyEndpoint extends BaseTechnologyEndpoint {
   private final DeleteTechnology useCase;

    public DeleteTechnologyEndpoint(DeleteTechnology useCase) {
        this.useCase = useCase;
    }

    @Operation(operationId = "delete-technology", summary = "Delete a technology", description = "This operation deletes a technology with the given UUID")
    @DeleteMapping("/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id) {
        DeleteTechnology.OutputPort result = this.useCase.execute(id);

        return switch (result) {
            case DeleteTechnology.OutputPort.Ok __ -> ResponseEntity.noContent().build();
            case DeleteTechnology.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
            case DeleteTechnology.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
