package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.mapper.CustomerMapper;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.repository.ExpertRepository;
import com.example.home_service_system.service.ExpertService;
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
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ExpertResponse save(ExpertSaveRequest expertSaveRequest) {
        return null;
    }

    @Override
    public ExpertResponse update(ExpertUpdateRequest expertUpdateRequest) {
        return null;
    }

    @Override
    public ExpertResponse findByIdAndIsDeletedFalse(Long id) {
        return null;
    }

    @Override
    public List<ExpertResponse> findAll() {
        return List.of();
    }

    @Override
    public ExpertResponse findByUsername(String username) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void changePassword(Long id, ExpertChangePasswordRequest request) {

    }
}
