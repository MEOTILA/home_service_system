package com.example.home_service_system.service;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ExpertService {
    ExpertResponse save(@Valid ExpertSaveRequest expertSaveRequest);

    ExpertResponse update(@Valid ExpertUpdateRequest expertUpdateRequest);

    byte[] getExpertImage(Long expertId);

    ExpertResponse findByIdAndIsDeletedFalse(Long id);

    Expert findExpertByIdAndIsDeletedFalse(Long id);

    List<ExpertResponse> findAllAndIsDeletedFalse();

    ExpertResponse findByUsernameAndIsDeletedFalse(String username);

    void changePassword(@Valid ExpertChangePasswordRequest request);

    void softDeleteExpertAndOrdersAndSuggestionsAndCommentAndRatesById(Long id);

    void softDeleteById(Long id);

    Page<ExpertResponse> getFilteredExperts(
            String firstName, String lastName, String username,
            String nationalId, String phoneNumber, String email,
            Integer rating, UserStatus userStatus, Long balance,
            LocalDate createdAt, LocalDate birthday,
            Long subServiceId, int page, int size);
}
