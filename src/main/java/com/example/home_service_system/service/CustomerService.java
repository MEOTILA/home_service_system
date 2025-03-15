package com.example.home_service_system.service;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface CustomerService {
    CustomerResponse save(@Valid CustomerSaveRequest customerSaveRequest);

    CustomerResponse update(@Valid CustomerUpdateRequest customerUpdateRequest);

    CustomerResponse findByIdAndIsDeletedFalse(Long id);

    List<CustomerResponse> findAll();

    CustomerResponse findByUsername(String username);

    void deleteById(Long id);

    void changePassword(Long id, @Valid CustomerChangePasswordRequest request);
}
