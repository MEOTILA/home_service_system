package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.entity.ExpertSuggestion;

public class ExpertSuggestionMapper {
    public static ExpertSuggestionResponse to(ExpertSuggestion expertSuggestion) {
        if (expertSuggestion == null) {
            return null;
        }
        return new ExpertSuggestionResponse(
                expertSuggestion.getId(),
                expertSuggestion.getOrder() != null ? expertSuggestion.getOrder().getId() : null,
                expertSuggestion.getExpert() != null ? expertSuggestion.getExpert().getId() : null,
                expertSuggestion.getExpertSuggestion(),
                expertSuggestion.getCreatedAt(),
                expertSuggestion.getUpdatedAt(),
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
        expertSuggestion.setExpert(request.expert());
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
               expertSuggestion.getOrder() != null ? expertSuggestion.getOrder() : null,
               expertSuggestion.getExpertSuggestion(),
               expertSuggestion.getExpertOfferedCost(),
               expertSuggestion.getServiceTimeDuration(),
               expertSuggestion.getExpertServiceStartDateTime()
       );

    }
}
