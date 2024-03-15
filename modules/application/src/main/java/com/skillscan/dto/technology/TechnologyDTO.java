package com.skillscan.dto.technology;

import com.skillscan.model.enums.Category;

import java.util.UUID;

public record TechnologyDTO(UUID id, Category category, String name) {}
