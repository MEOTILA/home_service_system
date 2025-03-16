package com.example.home_service_system.service.impl;

import com.example.home_service_system.mapper.MainServiceMapper;
import com.example.home_service_system.repository.MainServiceRepository;
import com.example.home_service_system.service.MainServiceService;
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
public class MainServiceServiceImpl implements MainServiceService {
    private final MainServiceRepository mainServiceRepository;
    private final MainServiceMapper mainServiceMapper;
    private final PasswordEncoder passwordEncoder;
}
