package com.skillscan.endpoint.user;

import com.skillscan.endpoint.BaseUserEndpoint;

import com.skillscan.model.enums.JobTitle;
import com.skillscan.model.enums.Roles;
import com.skillscan.usecase.user.CreateUser;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserEndpoint extends BaseUserEndpoint {
    private final CreateUser useCase;

    public CreateUserEndpoint(CreateUser useCase) {
        this.useCase = useCase;
    }

    public record Request(String username, String password, Roles role, JobTitle jobTitle) { }

    @CrossOrigin(value = "*")
    @Operation(summary = "Create a user")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreateUser.OutputPort result = this.useCase.execute(new CreateUser.InputPort(request.username(), request.password(), request.role(), request.jobTitle()));

        return switch (result) {
            case CreateUser.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateUser.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
