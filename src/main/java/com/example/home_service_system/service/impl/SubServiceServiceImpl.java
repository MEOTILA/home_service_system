package com.example.home_service_system.service.impl;

import com.example.home_service_system.mapper.SubServiceMapper;
import com.example.home_service_system.repository.SubServiceRepository;
import com.example.home_service_system.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository subServiceRepository;
    private final SubServiceMapper subServiceMapper;
    private final PasswordEncoder passwordEncoder;
}
