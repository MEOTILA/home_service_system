package com.example.home_service_system.dto.expertSuggestionDTO;

import java.time.Duration;
import java.time.LocalDateTime;

public record ExpertSuggestionResponse(
        Long id,
        Long orderId,
        String expertSuggestion,
        LocalDateTime suggestionSubmitDate,
        Long expertOfferedCost,
        Duration serviceTimeDuration,
        LocalDateTime expertServiceStartDateTime

) {
}
