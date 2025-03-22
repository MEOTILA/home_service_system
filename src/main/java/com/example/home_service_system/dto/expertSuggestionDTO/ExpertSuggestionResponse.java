package com.example.home_service_system.dto.expertSuggestionDTO;

import java.time.Duration;
import java.time.LocalDateTime;

public record ExpertSuggestionResponse(
        Long id,
        Long orderId,
        Long expertId,
        String expertSuggestion,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long expertOfferedCost,
        Duration serviceTimeDuration,
        LocalDateTime expertServiceStartDateTime

) {
}
