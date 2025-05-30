package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.mapper.SubServiceMapper;
import com.example.home_service_system.repository.SubServiceRepository;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.MainServiceService;
import com.example.home_service_system.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ExpertService expertService;
    private final MainServiceService mainServiceService;

    @Override
    public SubServiceResponse save(SubServiceSaveRequest request) {
        Optional<SubService> existingSubService = subServiceRepository.findByName(request.name());
        if (existingSubService.isPresent()) {
            throw new CustomApiException("SubService with name {" + request.name()
                    + "} is already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        MainService mainService = mainServiceService.
                findMainServiceByIdAndIsDeletedFalse(request.mainServiceId());

        SubService subService = SubServiceMapper.fromSaveRequest(request);
        subService.setMainService(mainService);
        subServiceRepository.save(subService);
        log.info("SubService with id {} created", subService.getId());

        return SubServiceMapper.to(subService);
    }

    @Override
    public SubServiceResponse update(SubServiceUpdateRequest request) {
        SubService existingSubService = findSubServiceByIdAndIsDeletedFalse(request.id());

        if (request.name() != null && !request.name().isBlank()) {
            existingSubService.setName(request.name());
        }
        if (request.baseCost() != null) {
            existingSubService.setBaseCost(request.baseCost());
        }
        if (request.description() != null && !request.description().isBlank()) {
            existingSubService.setDescription(request.description());
        }

        if (request.mainServiceId() != null) {
            MainService newMainService = mainServiceService.
                    findMainServiceByIdAndIsDeletedFalse(request.mainServiceId());
            if (!newMainService.getId().equals(existingSubService.getMainService().getId())) {
                existingSubService.setMainService(newMainService);
            }
        }

        SubService updatedSubService = subServiceRepository.save(existingSubService);
        log.info("SubService with id {} updated", updatedSubService.getId());

        return SubServiceMapper.to(updatedSubService);
    }

    @Override
    public SubServiceResponse findByIdAndIsDeletedFalse(Long id) {
        SubService subService = subServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("SubService with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return SubServiceMapper.to(subService);
    }

    @Override
    public SubService findSubServiceByIdAndIsDeletedFalse(Long id) {
        return subServiceRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("SubService with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<SubServiceResponse> findAllAndIsDeletedFalse() {
        List<SubService> subServices = subServiceRepository.findAllAndIsDeletedFalse();
        return subServices.stream().map(SubServiceMapper::to).toList();
    }

    @Override
    public List<SubService> findAllSubServicesAndIsDeletedFalse() {
        return subServiceRepository.findAllAndIsDeletedFalse();
    }

    @Override
    public List<SubServiceResponse> findAllByMainServiceId(Long mainServiceId) {
        List<SubService> subServices = subServiceRepository.
                findAllByMainServiceIdAndIsDeletedFalse(mainServiceId);
        return subServices.stream().map(SubServiceMapper::to).toList();
    }

    @Override
    public List<SubServiceResponse> findAllByExpertIdAndIsDeletedFalse(Long expertId){
        List<SubService> subServices =
                subServiceRepository.findAllByExpertIdAndIsDeletedFalse(expertId);
        return subServices.stream().map(SubServiceMapper::to).toList();
    }

    @Override
    public void softDeleteSubServiceAndExpertFieldsAndOrdersAndCommentAndRateAndSuggestionsById(Long id) {
        SubService deletingSubService = findSubServiceByIdAndIsDeletedFalse(id);

        for (Order order : deletingSubService.getOrderList()) {
            order.setDeleted(true);

            order.getExpertSuggestionList().forEach(suggestion -> suggestion.setDeleted(true));

            if (order.getCustomerCommentAndRate() != null) {
                order.getCustomerCommentAndRate().setDeleted(true);
            }
        }

        /*for (Expert expert : deletingSubService.getExpertList()) {
            expert.getExpertServiceFields().remove(deletingSubService);
            expertService.update(ExpertMapper.toUpdateRequest(expert));
        }*/

        //deletingSubService.setDeleted(true);
        //subServiceRepository.save(deletingSubService);
        subServiceRepository.softDeleteById(deletingSubService.getId());
        log.info("SubService with id {} and all related entities deleted", id);
    }

    @Override
    public void softDeleteById(Long id) {
        findSubServiceByIdAndIsDeletedFalse(id);
        subServiceRepository.softDeleteById(id);
        log.info("SubService with id {} deleted", id);
    }

    @Override
    public void addExpertToSubService(Long subServiceId, Long expertId) {
        SubService subService = findSubServiceByIdAndIsDeletedFalse(subServiceId);
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(expertId);

        if (subService.getExpertList().contains(expert)) {
            throw new CustomApiException("Expert with id {"
                    + expertId + "} is already assigned to SubService {"
                    + subServiceId + "}", CustomApiExceptionType.BAD_REQUEST);
        }

        expert.getExpertServiceFields().add(subService);
        subService.getExpertList().add(expert);

        expertService.update(ExpertMapper.toUpdateRequest(expert));

        log.info("Expert with id {} added to SubService with id {}", expertId, subServiceId);
    }

    @Override
    public void removeExpertFromSubService(Long subServiceId, Long expertId) {
        SubService subService = findSubServiceByIdAndIsDeletedFalse(subServiceId);
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(expertId);

        if (!subService.getExpertList().contains(expert)) {
            throw new CustomApiException("Expert with id {"
                    + expertId + "} is not assigned to SubService {"
                    + subServiceId + "}", CustomApiExceptionType.BAD_REQUEST);
        }
        expert.getExpertServiceFields().remove(subService);
        subService.getExpertList().remove(expert);
        expertService.update(ExpertMapper.toUpdateRequest(expert));
        log.info("Expert with id {} removed from SubService with id {}", expertId, subServiceId);
    }

}

