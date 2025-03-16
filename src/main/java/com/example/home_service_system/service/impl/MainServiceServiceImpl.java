package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.MainServiceMapper;
import com.example.home_service_system.repository.MainServiceRepository;
import com.example.home_service_system.service.MainServiceService;
import com.example.home_service_system.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class MainServiceServiceImpl implements MainServiceService {
    private final MainServiceRepository mainServiceRepository;
    private final MainServiceMapper mainServiceMapper;

    @Override
    public MainServiceResponse save(MainServiceSaveRequest request) {

        if (mainServiceRepository.findByName(request.name()).isPresent()) {
            throw new CustomApiException("MainService with this name already exists!"
                    , CustomApiExceptionType.BAD_REQUEST);
        }
        MainService mainService = mainServiceMapper.fromSaveRequest(request);
        mainServiceRepository.save(mainService);
        return mainServiceMapper.to(mainService);

    }

    @Override
    public MainServiceResponse update(MainServiceUpdateRequest request) {

        MainService mainService = mainServiceRepository.findByIdAndIsDeletedFalse(request.id())
                .orElseThrow(() -> new CustomApiException("MainService not found!"
                        , CustomApiExceptionType.NOT_FOUND));

        mainService.setName(request.name());
        return mainServiceMapper.to(mainService);
    }

    @Override
    public void softDelete(Long id) {

        mainServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("MainService not found!"
                        , CustomApiExceptionType.NOT_FOUND));
        log.info("MainService with id {} is deleted", id);

        mainServiceRepository.softDeleteById(id);
    }

    @Override
    public MainServiceResponse findById(Long id) {
        MainService mainService = mainServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("MainService not found!"
                        , CustomApiExceptionType.NOT_FOUND));

        return mainServiceMapper.to(mainService);
    }

    @Override
    public List<MainServiceResponse> findAll() {
        List<MainService> mainServices = mainServiceRepository.findAllByIsDeletedFalse();

        if (mainServices.isEmpty()) {
            throw new CustomApiException("No MainServices found!", CustomApiExceptionType.NOT_FOUND);
        }

        return mainServices.stream().map(mainServiceMapper::to).toList();
    }
}