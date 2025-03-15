package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.mapper.AdminMapper;
import com.example.home_service_system.mapper.CustomerMapper;
import com.example.home_service_system.repository.AdminRepository;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponse save(CustomerSaveRequest customerSaveRequest) {
        return null;
    }

    @Override
    public CustomerResponse update(CustomerUpdateRequest customerUpdateRequest) {
        return null;
    }

    @Override
    public CustomerResponse findByIdAndIsDeletedFalse(Long id) {
        return null;
    }

    @Override
    public List<CustomerResponse> findAll() {
        return List.of();
    }

    @Override
    public CustomerResponse findByUsername(String username) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void changePassword(Long id, CustomerChangePasswordRequest request) {

    }
}
