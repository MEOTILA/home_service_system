package com.example.home_service_system.service;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.entity.ExpertSuggestion;
import jakarta.validation.Valid;

import java.util.List;

public interface ExpertSuggestionService {

    ExpertSuggestionResponse save(@Valid ExpertSuggestionSaveRequest request);

    ExpertSuggestionResponse update(@Valid ExpertSuggestionUpdateRequest request);

    List<ExpertSuggestionResponse> findAllAndIsDeletedFalse();

    ExpertSuggestionResponse findByIdAndIsDeletedFalse(Long id);

    ExpertSuggestion findSuggestionByIdAndIsDeletedFalse(Long id);

    List<ExpertSuggestionResponse> findAllByExpertIdAndIsDeletedFalse(Long id);

    List<ExpertSuggestionResponse> findAllByOrderIdAndIsDeletedFalse(Long id);

    void softDeleteById(Long id);
}
