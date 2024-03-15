package com.skillscan.usecase.authentication;

import com.skillscan.model.enums.Roles;
import com.skillscan.model.user.User;
import com.skillscan.service.TokenService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

public class AuthenticationGenerateImpl implements AuthenticationGenerete {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationGenerateImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Transactional
    public OutputPort execute(InputPort inputPort) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(inputPort.username(), inputPort.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);
        Roles role = user.getRole();

        return new OutputPort.Ok(token, user.getId(), user.getUsername(), role);
    }
}
