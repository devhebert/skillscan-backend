package com.skillscan.endpoint.user;

import com.skillscan.endpoint.BaseUserEndpoint;
import com.skillscan.usecase.user.GetUserById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetUserByIdEndpoint extends BaseUserEndpoint {
    private final GetUserById usecase;

    public GetUserByIdEndpoint(GetUserById usecase) {
        this.usecase = usecase;
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute(@PathVariable("id") UUID id) {
        GetUserById.OutputPort result = usecase.execute(new GetUserById.InputPort(id));

        return switch (result) {
            case GetUserById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetUserById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
