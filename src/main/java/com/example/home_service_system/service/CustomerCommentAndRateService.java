package com.example.home_service_system.service;

import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateResponse;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateSaveRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateUpdateRequest;
import com.example.home_service_system.entity.CustomerCommentAndRate;
import jakarta.validation.Valid;

import java.util.List;

public interface CustomerCommentAndRateService {
    CustomerCommentAndRateResponse save(@Valid CustomerCommentAndRateSaveRequest request);

    CustomerCommentAndRateResponse update(@Valid CustomerCommentAndRateUpdateRequest request);

    void softDeleteById(Long id);

    CustomerCommentAndRateResponse findByIdAndIsDeletedFalse(Long id);

    CustomerCommentAndRate findCommentAndRateByIdAndIsDeletedFalse(Long id);

    List<CustomerCommentAndRateResponse> findAllAndIsDeletedFalse();
}
