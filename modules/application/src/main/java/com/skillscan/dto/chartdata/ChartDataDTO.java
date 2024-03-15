package com.skillscan.dto.chartdata;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChartDataDTO(UUID userId, String technologyName, Integer score, LocalDateTime testDate) {}
