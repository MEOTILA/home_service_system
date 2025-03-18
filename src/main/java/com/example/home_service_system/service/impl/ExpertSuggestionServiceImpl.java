package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.ExpertSuggestion;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.customMappers.CustomExpertSuggestionMapper;
import com.example.home_service_system.mapper.customMappers.CustomOrderMapper;
import com.example.home_service_system.repository.ExpertSuggestionRepository;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.ExpertSuggestionService;
import com.example.home_service_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class ExpertSuggestionServiceImpl implements ExpertSuggestionService {
    private final ExpertSuggestionRepository repository;
    private final ExpertService expertService;
    private final OrderService orderService;

    @Override
    public ExpertSuggestionResponse save(@Valid ExpertSuggestionSaveRequest request) {

        Order order = orderService.findOrderByIdAndIsDeletedFalse(request.order().getId());
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(request.expert().getId());

        if (!expert.getExpertServiceFields().contains(order.getSubService())) {
            throw new CustomApiException("Expert is not authorized to provide this service.",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        boolean alreadySuggested = order.getExpertSuggestionList().stream()
                .anyMatch(suggestion -> suggestion.getExpert().getId().equals(expert.getId()));
        if (alreadySuggested) {
            throw new CustomApiException("Expert has already submitted a suggestion for this order.",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        if (order.getExpert() != null) {
            throw new CustomApiException("This order has already been assigned to an expert."
                    , CustomApiExceptionType.BAD_REQUEST);
        }

        ExpertSuggestion expertSuggestion = CustomExpertSuggestionMapper.fromSaveRequest(request);
        expertSuggestion.setOrder(order);
        order.getExpertSuggestionList().add(expertSuggestion);

        OrderUpdateRequest orderUpdateRequest = CustomOrderMapper.toUpdateRequest(order);
        orderService.update(orderUpdateRequest);

        repository.save(expertSuggestion);
        log.info("ExpertSuggestion with id {} saved", expertSuggestion.getId());
        return CustomExpertSuggestionMapper.to(expertSuggestion);
    }

    @Override
    public ExpertSuggestionResponse update(@Valid ExpertSuggestionUpdateRequest request) {
        ExpertSuggestion existingSuggestion = repository.findByIdAndIsDeletedFalse(request.id())
                .orElseThrow(() -> new CustomApiException("ExpertSuggestion with id {"
                        + request.id() + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        if (StringUtils.hasText(request.expertSuggestion())) {
            existingSuggestion.setExpertSuggestion(request.expertSuggestion());
        }
        if (request.expertOfferedCost() != null) {
            existingSuggestion.setExpertOfferedCost(request.expertOfferedCost());
        }
        if (request.serviceTimeDuration() != null) {
            existingSuggestion.setServiceTimeDuration(request.serviceTimeDuration());
        }
        if (request.expertServiceStartDateTime() != null) {
            existingSuggestion.setExpertServiceStartDateTime(request.expertServiceStartDateTime());
        }

        repository.save(existingSuggestion);
        log.info("ExpertSuggestion with id {} updated", existingSuggestion.getId());
        return CustomExpertSuggestionMapper.to(existingSuggestion);
    }

    @Override
    public List<ExpertSuggestionResponse> findAllByIsDeletedFalse() {
        List<ExpertSuggestion> foundedItems = repository.findAllByIsDeletedFalse();
        return foundedItems.stream()
                .map(CustomExpertSuggestionMapper::to)
                .toList();
    }

    @Override
    public List<ExpertSuggestionResponse> findAllByExpertIdAndIsDeletedFalse(Long id){
        List<ExpertSuggestion> foundedItems = repository.findAllByExpertIdAndIsDeletedFalse(id);
        return foundedItems.stream().map(CustomExpertSuggestionMapper::to)
                .toList();
    }

    @Override
    public List<ExpertSuggestionResponse> findAllByOrderIdAndIsDeletedFalse(Long id){
        List<ExpertSuggestion> foundedItems = repository.findAllByOrderIdAndIsDeletedFalse(id);
        return foundedItems.stream().map(CustomExpertSuggestionMapper::to)
                .toList();
    }
    @Override
    public void softDeleteById(Long id) {
        repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("ExpertSuggestion with id {"
                        + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        repository.softDeleteById(id);
        log.info("ExpertSuggestion with id {} deleted", id);
    }

    /*

    @Override
    public ExpertSuggestionResponse findById(Long id) {
        ExpertSuggestion expertSuggestion = expertService.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("ExpertSuggestion with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return CustomExpertSuggestionMapper.to(expertSuggestion);
    }

    @Override
    public List<ExpertSuggestionResponse> findAllByOrderId(Long orderId) {
        return expertService.findAllByOrderIdAndIsDeletedFalse(orderId).stream()
                .map(CustomExpertSuggestionMapper::to)
                .toList();
    }

    */
}