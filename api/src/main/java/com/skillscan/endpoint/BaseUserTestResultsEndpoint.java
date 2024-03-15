package com.skillscan.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "Get user test results", description = "The Get user test results API")
@RestController
@RequestMapping("/api/v1/user-test-results")
public abstract class BaseUserTestResultsEndpoint {}


