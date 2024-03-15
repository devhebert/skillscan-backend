package com.skillscan.dto.course;

import java.util.List;
import java.util.UUID;

public record CourseDTO(UUID id, UUID technologyId ,String name, String content, List<String> keywords) {}
