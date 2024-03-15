package com.skillscan.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "Technology", description = "The Technology API")
@RestController
@RequestMapping(value = "/api/v1/technology")
public abstract class BaseTechnologyEndpoint {}
