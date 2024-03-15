package com.skillscan.endpoint.authentication;

import com.skillscan.endpoint.BaseLoginEndpoint;
import com.skillscan.usecase.authentication.AuthenticationGenerete;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationLoginEndpoint extends BaseLoginEndpoint {
     private final AuthenticationGenerete useCase;

    public AuthenticationLoginEndpoint(AuthenticationGenerete useCase) {
        this.useCase = useCase;
    }

    public record Request(String username, String password){}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Request data) {
        AuthenticationGenerete.OutputPort result = this.useCase.execute(new AuthenticationGenerete.InputPort(data.username(), data.password()));

        return switch (result) {
            case AuthenticationGenerete.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case AuthenticationGenerete.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
