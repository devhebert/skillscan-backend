package com.skillscan.endpoint.user;

import com.skillscan.endpoint.BaseUserEndpoint;
import com.skillscan.usecase.user.DeleteUser;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteUserEndpoint extends BaseUserEndpoint {
    private final DeleteUser useCase;

    public DeleteUserEndpoint(DeleteUser useCase) {
        this.useCase = useCase;
    }

    @Operation(operationId = "delete-user", summary = "Delete a user", description = "This operation deletes a user with the given UUID")
    @DeleteMapping("/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id) {
        DeleteUser.OutputPort result = this.useCase.execute(id);

        return switch (result) {
            case DeleteUser.OutputPort.Ok __ -> ResponseEntity.noContent().build();
            case DeleteUser.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
            case DeleteUser.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
