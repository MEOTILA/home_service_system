package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.mapper.MainServiceMapper;
import com.example.home_service_system.mapper.SubServiceMapper;
import com.example.home_service_system.repository.SubServiceRepository;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.MainServiceService;
import com.example.home_service_system.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository subServiceRepository;
    private final SubServiceMapper subServiceMapper;
    private final ExpertMapper expertMapper;
    private final MainServiceMapper mainServiceMapper;
    private final ExpertService expertService;
    private final MainServiceService mainServiceService;

    @Override
    public SubServiceResponse save(SubServiceSaveRequest request) {
        Optional<SubService> existingSubService = subServiceRepository.findByName(request.name());
        if (existingSubService.isPresent()) {
            throw new CustomApiException("SubService with name {" + request.name() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }

        SubService subService = subServiceMapper.fromSaveRequest(request);
        SubService savedSubService = subServiceRepository.save(subService);
        log.info("SubService with id {} created", savedSubService.getId());

        return subServiceMapper.to(savedSubService);
    }

    @Override
    public SubServiceResponse update(SubServiceUpdateRequest request) {
        SubService existingSubService = subServiceRepository.findByIdAndIsDeletedFalse(request.id())
                .orElseThrow(() -> new CustomApiException("SubService with id {" + request.id() + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        if (request.name() != null && !request.name().isBlank()) {
            existingSubService.setName(request.name());
        }
        if (request.baseCost() != null) {
            existingSubService.setBaseCost(request.baseCost());
        }
        if (request.description() != null && !request.description().isBlank()) {
            existingSubService.setDescription(request.description());
        }

        SubService updatedSubService = subServiceRepository.save(existingSubService);
        log.info("SubService with id {} updated", updatedSubService.getId());

        return subServiceMapper.to(updatedSubService);
    }

    @Override
    public SubServiceResponse findById(Long id) {
        SubService subService = subServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("SubService with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return subServiceMapper.to(subService);
    }

    @Override
    public List<SubServiceResponse> findAllByIsDeletedFalse() {
        List<SubService> subServices = subServiceRepository.findAllByIsDeletedFalse();
        return subServices.stream().map(subServiceMapper::to).toList();
    }

    @Override
    public List<SubService> findAllSubServicesByIsDeletedFalse() {
        return subServiceRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<SubServiceResponse> findAllByMainServiceId(Long mainServiceId) {
        List<SubService> subServices = subServiceRepository.findAllByMainServiceIdAndIsDeletedFalse(mainServiceId);
        return subServices.stream().map(subServiceMapper::to).toList();
    }

    @Override
    public void softDeleteById(Long id) {
        subServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("SubService with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        subServiceRepository.softDeleteById(id);
        log.info("SubService with id {} deleted", id);
    }

    @Override
    public void softDeleteAllByMainServiceId(Long mainServiceId) {
        List<SubService> subServices = subServiceRepository
                .findAllByMainServiceIdAndIsDeletedFalse(mainServiceId);
        subServices.forEach(subService -> softDeleteById(subService.getId()));
        /*for (SubService subService : subServices) {
            for (Expert expert : subService.getExpertList()) {
                expert.getExpertServiceFields().remove(subService);
                expertService.update(expertMapper.toUpdateRequest(expert));
            }
            softDeleteById(subService.getId());
        }*/
        mainServiceService.softDelete(mainServiceId);
        log.info("All SubServices related to MainService with ID {} were soft deleted"
                , mainServiceId);
    }

    @Override
    public void addExpertToSubService(Long subServiceId, Long expertId) {
        SubService subService = subServiceRepository.findByIdAndIsDeletedFalse(subServiceId)
                .orElseThrow(() -> new CustomApiException("SubService with id {" + subServiceId + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(expertId);

        if (subService.getExpertList().contains(expert)) {
            throw new CustomApiException("Expert with id {"
                    + expertId + "} is already assigned to SubService {"
                    + subServiceId + "}", CustomApiExceptionType.BAD_REQUEST);
        }

        expert.getExpertServiceFields().add(subService);
        subService.getExpertList().add(expert);

        ExpertUpdateRequest expertUpdateRequest = expertMapper.toUpdateRequest(expert);
        expertService.update(expertUpdateRequest);

        log.info("Expert with id {} added to SubService with id {}", expertId, subServiceId);
    }

    @Override
    public void removeExpertFromSubService(Long subServiceId, Long expertId) {
        SubService subService = subServiceRepository.findByIdAndIsDeletedFalse(subServiceId)
                .orElseThrow(() -> new CustomApiException("SubService with id {"
                        + subServiceId + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(expertId);

        if (!subService.getExpertList().contains(expert)) {
            throw new CustomApiException("Expert with id {"
                    + expertId + "} is not assigned to SubService {"
                    + subServiceId + "}", CustomApiExceptionType.BAD_REQUEST);
        }
        expert.getExpertServiceFields().remove(subService);
        subService.getExpertList().remove(expert);
        expertService.update(expertMapper.toUpdateRequest(expert));
        //subServiceRepository.save(subService);
        log.info("Expert with id {} removed from SubService with id {}", expertId, subServiceId);
    }

    @Override
    public SubServiceResponse updateSubService(Long subServiceId, SubServiceUpdateRequest updateRequest) {
        SubService subService = subServiceRepository.findByIdAndIsDeletedFalse(subServiceId)
                .orElseThrow(() -> new CustomApiException("SubService with id {"
                        + subServiceId + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        if (updateRequest.name() != null && !updateRequest.name().isEmpty()) {
            subService.setName(updateRequest.name());
        }

        if (updateRequest.baseCost() != null) {
            subService.setBaseCost(updateRequest.baseCost());
        }

        if (updateRequest.description() != null && !updateRequest.description().isEmpty()) {
            subService.setDescription(updateRequest.description());
        }

        if (updateRequest.mainService() != null && updateRequest.mainService().getId() != null) {
            MainServiceResponse mainServiceResponse = mainServiceService.findById(updateRequest.mainService().getId());

            MainService mainService = mainServiceMapper.toMainServiceFromResponse(mainServiceResponse);

            if (mainService == null) {
                throw new CustomApiException(
                        "MainService with id {" + updateRequest.mainService().getId() + "} not found!",
                        CustomApiExceptionType.NOT_FOUND);
            }

            subService.setMainService(mainService);
        }

        SubService updatedSubService = subServiceRepository.save(subService);
        log.info("SubService with id {} updated", updatedSubService.getId());
        return subServiceMapper.to(updatedSubService);
    }


}

