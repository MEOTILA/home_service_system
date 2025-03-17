package com.example.home_service_system.mapper.customMappers;

import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.entity.ExpertSuggestion;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CustomExpertSuggestionMapper {
    public static ExpertSuggestionResponse to(ExpertSuggestion expertSuggestion) {
        if (expertSuggestion == null) {
            return null;
        }
        return new ExpertSuggestionResponse(
                expertSuggestion.getId(),
                expertSuggestion.getOrder() != null ? expertSuggestion.getOrder().getId() : null,
                expertSuggestion.getExpertSuggestion(),
                expertSuggestion.getSuggestionSubmitDate(),
                expertSuggestion.getExpertOfferedCost(),
                expertSuggestion.getServiceTimeDuration(),
                expertSuggestion.getExpertServiceStartDateTime()
        );
    }

    public static ExpertSuggestion fromSaveRequest(ExpertSuggestionSaveRequest request) {
        if (request == null) {
            return null;
        }
        ExpertSuggestion expertSuggestion = new ExpertSuggestion();
        expertSuggestion.setOrder(request.order());
        expertSuggestion.setExpertSuggestion(request.expertSuggestion());
        expertSuggestion.setExpertOfferedCost(request.expertOfferedCost());
        expertSuggestion.setServiceTimeDuration(request.serviceTimeDuration());
        expertSuggestion.setExpertServiceStartDateTime(request.expertServiceStartDateTime());
        return expertSuggestion;
    }

    public static ExpertSuggestion fromUpdateRequest(ExpertSuggestionUpdateRequest request) {
        if (request == null) {
            return null;
        }
        ExpertSuggestion expertSuggestion = new ExpertSuggestion();
        expertSuggestion.setId(request.id());
        expertSuggestion.setOrder(request.order());
        expertSuggestion.setExpertSuggestion(request.expertSuggestion());
        expertSuggestion.setExpertOfferedCost(request.expertOfferedCost());
        expertSuggestion.setServiceTimeDuration(request.serviceTimeDuration());
        expertSuggestion.setExpertServiceStartDateTime(request.expertServiceStartDateTime());
        return expertSuggestion;
    }

    public static ExpertSuggestionUpdateRequest toUpdateRequest(ExpertSuggestion expertSuggestion) {
       return new ExpertSuggestionUpdateRequest(
               expertSuggestion.getId(),
               expertSuggestion.getOrder(),
               expertSuggestion.getExpertSuggestion(),
               expertSuggestion.getExpertOfferedCost(),
               expertSuggestion.getServiceTimeDuration(),
               expertSuggestion.getExpertServiceStartDateTime()
       );

    }
}
