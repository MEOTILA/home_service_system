package com.example.home_service_system.service;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    CustomerResponse save(@Valid CustomerSaveRequest customerSaveRequest) throws MessagingException;

    CustomerResponse update(@Valid CustomerUpdateRequest customerUpdateRequest);

    CustomerResponse findByIdAndIsDeletedFalse(Long id);

    Customer findCustomerByIdAndIsDeletedFalse(Long id);

    List<CustomerResponse> findAllAndIsDeletedFalse();

    CustomerResponse findByUsernameAndIsDeletedFalse(String username);

    void changePassword(@Valid CustomerChangePasswordRequest request);

    void softDeleteCustomerAndOrdersAndSuggestionsAndCommentAndRateById(Long id);

    void softDeleteById(Long id);
}
