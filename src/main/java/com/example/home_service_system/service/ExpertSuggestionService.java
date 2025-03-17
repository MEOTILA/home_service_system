package com.example.home_service_system.service;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface ExpertSuggestionService {

    ExpertSuggestionResponse save(@Valid ExpertSuggestionSaveRequest request);

    List<ExpertSuggestionResponse> findAllByIsDeletedFalse();
}
