package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.ExpertSuggestion;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.ExpertSuggestionMapper;
import com.example.home_service_system.mapper.OrderMapper;
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

import java.util.Comparator;
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

        Order order = orderService.findOrderByIdAndIsDeletedFalse(request.orderId());
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(request.expertId());

        if (!expert.getExpertServiceFields().contains(order.getSubService()))
            throw new CustomApiException("Expert service field is not same to this order.",
                    CustomApiExceptionType.UNAUTHORIZED);

        boolean alreadySuggested = order.getExpertSuggestionList().stream()
                .anyMatch(suggestion -> suggestion.getExpert().getId().equals(expert.getId()));
        if (alreadySuggested)
            throw new CustomApiException("""
                    Expert has already submitted a suggestion for this order.
                    Please use update for suggestion.
                    """, CustomApiExceptionType.UNAUTHORIZED);

        if (order.getExpert() != null)
            throw new CustomApiException("This order has already been assigned to an expert."
                    , CustomApiExceptionType.BAD_REQUEST);

        if (!order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_TO_RESPONSE))
            throw new CustomApiException("This order has an accepted expert!"
                    , CustomApiExceptionType.BAD_REQUEST);

        ExpertSuggestion expertSuggestion = ExpertSuggestionMapper.fromSaveRequest(request);
        expertSuggestion.setOrder(order);
        expertSuggestion.setExpert(expert);
        order.setStatus(OrderStatus.WAITING_FOR_CUSTOMER_TO_ACCEPT);
        orderService.update(OrderMapper.toUpdateRequest(order));

        /*order.getExpertSuggestionList().add(expertSuggestion);
        orderService.update(OrderMapper.toUpdateRequest(order));*/

        repository.save(expertSuggestion);
        log.info("ExpertSuggestion with id {} saved", expertSuggestion.getId());
        return ExpertSuggestionMapper.to(expertSuggestion);
    }

    @Override
    public ExpertSuggestionResponse update(@Valid ExpertSuggestionUpdateRequest request) {
        ExpertSuggestion existingSuggestion = findSuggestionByIdAndIsDeletedFalse(request.id());

        if (StringUtils.hasText(request.expertSuggestion()))
            existingSuggestion.setExpertSuggestion(request.expertSuggestion());

        if (request.expertOfferedCost() != null)
            existingSuggestion.setExpertOfferedCost(request.expertOfferedCost());

        if (request.serviceTimeDuration() != null)
            existingSuggestion.setServiceTimeDuration(request.serviceTimeDuration());

        if (request.expertServiceStartDateTime() != null)
            existingSuggestion.setExpertServiceStartDateTime(request.expertServiceStartDateTime());

        repository.save(existingSuggestion);
        log.info("ExpertSuggestion with id {} updated", existingSuggestion.getId());
        return ExpertSuggestionMapper.to(existingSuggestion);
    }

    @Override
    public List<ExpertSuggestionResponse> findAllAndIsDeletedFalse() {
        List<ExpertSuggestion> foundedItems = repository.findAllAndIsDeletedFalse();
        return foundedItems.stream().map(ExpertSuggestionMapper::to).toList();
    }

    @Override
    public ExpertSuggestionResponse findByIdAndIsDeletedFalse(Long id) {
        ExpertSuggestion expertSuggestion = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert Suggestion with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));

        return ExpertSuggestionMapper.to(expertSuggestion);

    }

    @Override
    public ExpertSuggestion findSuggestionByIdAndIsDeletedFalse(Long id) {
        return repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert Suggestion with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<ExpertSuggestionResponse> findAllByExpertIdAndIsDeletedFalse(Long id) {
        List<ExpertSuggestion> foundedItems = repository.findAllByExpertIdAndIsDeletedFalse(id);
        return foundedItems.stream().map(ExpertSuggestionMapper::to).toList();
    }

    @Override
    public List<ExpertSuggestionResponse> findAllByOrderIdAndIsDeletedFalse(Long id) {
        List<ExpertSuggestion> foundedItems = repository.findAllByOrderIdAndIsDeletedFalse(id);
        return foundedItems.stream().map(ExpertSuggestionMapper::to).toList();
    }

    @Override
    public List<ExpertSuggestionResponse> findAllSortedByOrderIdAndIsDeletedFalse(Long orderId, String sortBy) {
        List<ExpertSuggestion> foundedItems = repository.findAllByOrderIdAndIsDeletedFalse(orderId);

        if ("cost".equalsIgnoreCase(sortBy)) {
            foundedItems.sort(Comparator.comparing(ExpertSuggestion::getExpertOfferedCost));
        } else if ("rating".equalsIgnoreCase(sortBy)) {
            foundedItems.sort(Comparator.comparing(suggestion ->
                    suggestion.getExpert().getRating(), Comparator.reverseOrder()));
        }

        return foundedItems.stream().map(ExpertSuggestionMapper::to).toList();
    }

    @Override
    public void softDeleteById(Long id) {
        findSuggestionByIdAndIsDeletedFalse(id);
        repository.softDeleteById(id);
        log.info("ExpertSuggestion with id {} deleted", id);
    }
}