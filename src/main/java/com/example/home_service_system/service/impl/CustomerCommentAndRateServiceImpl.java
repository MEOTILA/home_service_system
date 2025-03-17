package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateResponse;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateSaveRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateUpdateRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.customMappers.CustomCustomerCommentAndRateMapper;
import com.example.home_service_system.mapper.customMappers.CustomExpertMapper;
import com.example.home_service_system.repository.CustomerCommentAndRateRepository;
import com.example.home_service_system.repository.OrderRepository;
import com.example.home_service_system.service.CustomerCommentAndRateService;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class CustomerCommentAndRateServiceImpl implements CustomerCommentAndRateService {

    private final CustomerCommentAndRateRepository repository;
    private final OrderService orderService;
    private final ExpertService expertService;

    @Override
    public CustomerCommentAndRateResponse save(@Valid CustomerCommentAndRateSaveRequest request) {
        Order order = orderService.findOrderByIdAndIsDeletedFalse(request.order().getId());

        CustomerCommentAndRate commentAndRate = CustomCustomerCommentAndRateMapper
                .fromSaveRequest(request);

        Expert expert = expertService
                .findExpertByIdAndIsDeletedFalse(commentAndRate.getOrder().getExpert().getId());
        Integer currentRating = expert.getRating();
        Integer finalRating = currentRating / 2;
        expert.setRating(finalRating);
        ExpertUpdateRequest expertUpdateRequest = CustomExpertMapper.toUpdateRequest(expert);
        expertService.update(expertUpdateRequest);


        commentAndRate.setOrder(order);
        order.setCustomerCommentAndRate(commentAndRate);
        log.info("customer comment and rate with id {} is saved", commentAndRate.getId());
        return CustomCustomerCommentAndRateMapper.to(repository.save(commentAndRate));
    }

    @Override
    public CustomerCommentAndRateResponse update(@Valid CustomerCommentAndRateUpdateRequest request) {
        CustomerCommentAndRate existingCommentAndRate =
                repository.findByIdAndIsDeletedFalse(request.id())
                        .orElseThrow(() -> new CustomApiException("Comment and Rate not found!"
                                , CustomApiExceptionType.NOT_FOUND));


        existingCommentAndRate.setOrder(request.order());
        existingCommentAndRate.setRating(request.rating());
        existingCommentAndRate.setComment(request.comment());

        Order order = orderService.findOrderByIdAndIsDeletedFalse(request.order().getId());
        if (order.getExpert() == null) {
            throw new CustomApiException("Order does not have an assigned expert!",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        Expert expert = expertService
                .findExpertByIdAndIsDeletedFalse(order.getExpert().getId());
        Integer currentRating = expert.getRating();
        Integer finalRating = request.rating() + currentRating / 2;
        expert.setRating(finalRating);
        ExpertUpdateRequest expertUpdateRequest = CustomExpertMapper.toUpdateRequest(expert);
        expertService.update(expertUpdateRequest);

        log.info("customer comment and rate with id {} is updated"
                , existingCommentAndRate.getId());

        return CustomCustomerCommentAndRateMapper
                .to(repository.save(existingCommentAndRate));
    }

    @Override
    public void softDeleteById(Long id) {
        repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Comment and Rate not found!"
                        , CustomApiExceptionType.NOT_FOUND));
        repository.softDeleteById(id);
        log.info("customer comment and rate with id {} deleted", id);

    }

    @Override
    public CustomerCommentAndRateResponse findByIdAndIsDeletedFalse(Long id) {
        CustomerCommentAndRate commentAndRate = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Comment and Rate not found!"
                        , CustomApiExceptionType.NOT_FOUND));
        return CustomCustomerCommentAndRateMapper.to(commentAndRate);
    }

    @Override
    public List<CustomerCommentAndRateResponse> findAllAndIsDeletedFalse() {
        List<CustomerCommentAndRate> comments = repository.findAllByIsDeletedFalse();
        if (comments.isEmpty()) {
            throw new CustomApiException("No comments found!"
                    , CustomApiExceptionType.NOT_FOUND);
        }
        return comments.stream()
                .map(CustomCustomerCommentAndRateMapper::to)
                .collect(Collectors.toList());
    }
}