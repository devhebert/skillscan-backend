package com.skillscan.dto.usertestresults;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserTestResultsDTO(UUID userId, String username, UUID technologyId, String technologyName, Integer score, Integer courseSuggestionsNumber, LocalDateTime testDate) {
}
