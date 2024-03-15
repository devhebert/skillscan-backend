package com.skillscan.service;

import com.skillscan.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilterComponent extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    Logger logger = org.slf4j.LoggerFactory.getLogger(SecurityFilterComponent.class);

    public SecurityFilterComponent(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.recoverToken(request);
            if (token != null) {
                String subject = tokenService.validateToken(token);
                Optional<UserDetails> user = userRepository.findByUsername(subject);

                if (user.isPresent()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.warn("Authenticated user: {}", user.get().getUsername());
                    logger.warn("User roles: {}", user.get().getAuthorities());
                } else {
                    logger.warn("User not found for token subject: {}", subject);
                }
            }
        } catch (Exception e) {
            logger.error("Error processing security filter", e);
        }

        logger.debug("Request method: {}", request.getMethod());
        logger.debug("Request URI: {}", request.getRequestURI());

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
