package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.customMappers.CustomMainServiceMapper;
import com.example.home_service_system.repository.MainServiceRepository;
import com.example.home_service_system.service.MainServiceService;
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

    @Override
    public MainServiceResponse save(MainServiceSaveRequest request) {

        if (mainServiceRepository.findByName(request.name()).isPresent()) {
            throw new CustomApiException("MainService with this name {"
                    + request.name() + "} already exists!"
                    , CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        MainService mainService = CustomMainServiceMapper.fromSaveRequest(request);
        mainServiceRepository.save(mainService);
        log.info("MainService with id {} is saved", mainService.getId());
        return CustomMainServiceMapper.to(mainService);

    }

    @Override
    public MainServiceResponse update(MainServiceUpdateRequest request) {

        MainService mainService = mainServiceRepository.findByIdAndIsDeletedFalse(request.id())
                .orElseThrow(() -> new CustomApiException("MainService with id {" +
                        request.id()+ "} not found!"
                        , CustomApiExceptionType.NOT_FOUND));

        if (request.name() != null && !request.name().isBlank()) {
            mainService.setName(request.name());
        }
        mainServiceRepository.save(mainService);
        log.info("MainService with id {} is updated", request.id());
        return CustomMainServiceMapper.to(mainService);
    }

    @Override
    public void softDelete(Long id) {

        mainServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("MainService with id {" +
                        id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        log.info("MainService with id {} is deleted", id);
        mainServiceRepository.softDeleteById(id);
    }

    @Override
    public MainServiceResponse findByIdAndIsDeletedFalse(Long id) {
        MainService mainService = mainServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("MainService with id {" +
                        id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomMainServiceMapper.to(mainService);
    }

    @Override
    public MainService findMainServiceByIdAndIsDeletedFalse(Long id) {
        return mainServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("MainService with id {" +
                        id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<MainServiceResponse> findAllByIsDeletedFalse() {
        List<MainService> mainServices = mainServiceRepository.findAllByIsDeletedFalse();
        if (mainServices.isEmpty()) {
            throw new CustomApiException("No MainServices found!",
                    CustomApiExceptionType.NOT_FOUND);
        }
        return mainServices.stream().map(CustomMainServiceMapper::to).toList();
    }
}