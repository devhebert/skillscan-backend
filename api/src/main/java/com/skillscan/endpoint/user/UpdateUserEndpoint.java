package com.skillscan.endpoint.user;

import com.skillscan.endpoint.BaseUserEndpoint;

import com.skillscan.model.enums.JobTitle;
import com.skillscan.model.enums.Roles;
import com.skillscan.usecase.user.UpdateUser;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UpdateUserEndpoint extends BaseUserEndpoint {
    private final UpdateUser useCase;

    public UpdateUserEndpoint(UpdateUser useCase) {
        this.useCase = useCase;
    }

    public record Request(String username, String password, Roles role, JobTitle jobTitle) {}

    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    ResponseEntity<?> execute(@PathVariable UUID id, @RequestBody @Valid Request request) {
        UpdateUser.OutputPort result = this.useCase.execute(new UpdateUser.InputPort(id, request.username(), request.password(), request.role(), request.jobTitle()));

        return switch (result) {
            case UpdateUser.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case UpdateUser.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
            case UpdateUser.OutputPort.NotFound __ -> ResponseEntity.notFound().build();
        };
    }
}
