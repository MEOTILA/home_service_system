package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateResponse;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateSaveRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateUpdateRequest;
import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.CustomerCommentAndRateMapper;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.repository.CustomerCommentAndRateRepository;
import com.example.home_service_system.service.CustomerCommentAndRateService;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
        Order order = orderService.findOrderByIdAndIsDeletedFalse(request.orderId());
        if (order.getExpert() == null)
            throw new CustomApiException("Order does not have an assigned expert!",
                    CustomApiExceptionType.BAD_REQUEST);

        if (order.getCustomerCommentAndRate() != null)
            throw new CustomApiException("A comment for this order already exists!",
                    CustomApiExceptionType.BAD_REQUEST);

        CustomerCommentAndRate commentAndRate = CustomerCommentAndRateMapper
                .fromSaveRequest(request);
        commentAndRate.setOrder(order);
        order.setCustomerCommentAndRate(commentAndRate);
        repository.save(commentAndRate);

        Expert expert = expertService
                .findExpertByIdAndIsDeletedFalse(commentAndRate.getOrder().getExpert().getId());
        Integer averageRating = repository.calculateAverageRatingByExpertId(expert.getId());
        expert.setRating(averageRating);
        expertService.update(ExpertMapper.toUpdateRequest(expert));

        log.info("customer comment and rate with id {} is saved", commentAndRate.getId());
        return CustomerCommentAndRateMapper.to(repository.save(commentAndRate));
    }

    @Override
    public CustomerCommentAndRateResponse update(@Valid CustomerCommentAndRateUpdateRequest request) {
        CustomerCommentAndRate existingCommentAndRate =
                findCommentAndRateByIdAndIsDeletedFalse(request.id());

        /*if (request.order() != null)
            existingCommentAndRate.setOrder(request.order());*/

        if (request.rating() != null)
            existingCommentAndRate.setRating(request.rating());

        if (StringUtils.hasText(request.comment()))
            existingCommentAndRate.setComment(request.comment());

        //Order order = orderService.findOrderByIdAndIsDeletedFalse(request.orderId());
        Order order = orderService
                .findOrderByIdAndIsDeletedFalse(existingCommentAndRate.getOrder().getId());
        if (order.getExpert() == null)
            throw new CustomApiException("Order does not have an assigned expert!",
                    CustomApiExceptionType.BAD_REQUEST);

        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(order.getExpert().getId());
        Integer averageRating = repository.calculateAverageRatingByExpertId(expert.getId());
        expert.setRating(averageRating);
        expertService.update(ExpertMapper.toUpdateRequest(expert));

        log.info("customer comment and rate with id {} is updated"
                , existingCommentAndRate.getId());

        return CustomerCommentAndRateMapper
                .to(repository.save(existingCommentAndRate));
    }

    @Override
    public void softDeleteById(Long id) {
        findCommentAndRateByIdAndIsDeletedFalse(id);
        repository.softDeleteById(id);
        log.info("customer comment and rate with id {} deleted", id);

    }

    @Override
    public CustomerCommentAndRateResponse findByIdAndIsDeletedFalse(Long id) {
        CustomerCommentAndRate commentAndRate = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Comment and Rate with id {"
                        + id +"} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomerCommentAndRateMapper.to(commentAndRate);
    }

    @Override
    public CustomerCommentAndRate findCommentAndRateByIdAndIsDeletedFalse(Long id) {
        return repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Comment and Rate with id {"
                        + id +"} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<CustomerCommentAndRateResponse> findAllAndIsDeletedFalse() {
        List<CustomerCommentAndRate> comments = repository.findAllAndIsDeletedFalse();
        if (comments.isEmpty()) {
            throw new CustomApiException("No comments found!"
                    , CustomApiExceptionType.NOT_FOUND);
        }
        return comments.stream()
                .map(CustomerCommentAndRateMapper::to)
                .collect(Collectors.toList());
    }
}