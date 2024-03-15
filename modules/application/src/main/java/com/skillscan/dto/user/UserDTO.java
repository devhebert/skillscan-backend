package com.skillscan.dto.user;

import com.skillscan.model.enums.JobTitle;

import java.util.UUID;

public record UserDTO(UUID id, String username, String role, JobTitle jobTitle) {}
