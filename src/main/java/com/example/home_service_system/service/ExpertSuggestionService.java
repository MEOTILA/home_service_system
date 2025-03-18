package com.example.home_service_system.service;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface ExpertSuggestionService {

    ExpertSuggestionResponse save(@Valid ExpertSuggestionSaveRequest request);

    ExpertSuggestionResponse update(@Valid ExpertSuggestionUpdateRequest request);

    List<ExpertSuggestionResponse> findAllByIsDeletedFalse();
}
