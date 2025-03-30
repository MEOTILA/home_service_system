package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.orderDTO.OrderResponse;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.*;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.OrderMapper;
import com.example.home_service_system.repository.OrderRepository;
import com.example.home_service_system.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;

    @Override
    public OrderResponse save(@Valid OrderSaveRequest request) {
        Order order = OrderMapper.fromSaveRequest(request);

        Customer customer = customerService
                .findCustomerByIdAndIsDeletedFalse(request.customerId());
        order.setCustomer(customer);

        SubService subService = subServiceService
                .findSubServiceByIdAndIsDeletedFalse(request.subServiceId());
        order.setSubService(subService);

        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_RESPONSE);

        orderRepository.save(order);
        log.info("Order with id {} saved", order.getId());
        return OrderMapper.to(order);
    }

    @Override
    public OrderResponse update(@Valid OrderUpdateRequest request) {
        Order order = findOrderByIdAndIsDeletedFalse(request.id());

        if (request.subServiceId() != null) {
            SubService subService = subServiceService
                    .findSubServiceByIdAndIsDeletedFalse(request.subServiceId());
            order.setSubService(subService);
        }
        if (request.customerId() != null) {
            Customer customer = customerService
                    .findCustomerByIdAndIsDeletedFalse(request.customerId());
            order.setCustomer(customer);
        }
        if (request.expertId() != null) {
            Expert expert = expertService.findExpertByIdAndIsDeletedFalse(request.expertId());
            order.setExpert(expert);
        }
        if (request.customerOfferedCost() != null) {
            order.setCustomerOfferedCost(request.customerOfferedCost());
        }
        if (StringUtils.hasText(request.customerDescription())) {
            order.setCustomerDescription(request.customerDescription());
        }
        if (request.serviceDate() != null) {
            order.setServiceDate(request.serviceDate());
        }
        if (StringUtils.hasText(request.address())) {
            order.setAddress(request.address());
        }
        if (request.status() != null) {
            order.setStatus(request.status());
        }
        orderRepository.save(order);
        log.info("Order with id {} updated", order.getId());
        return OrderMapper.to(order);
    }

    @Override
    public OrderResponse findByIdAndIsDeletedFalse(Long id) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Order with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return OrderMapper.to(order);
    }

    @Override
    public Order findOrderByIdAndIsDeletedFalse(Long id) {
        return orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Order with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<OrderResponse> findAllAndIsDeletedFalse() {
        return orderRepository.findAllAndIsDeletedFalse().stream()
                .map(OrderMapper::to).toList();
    }


    @Override
    public List<OrderResponse> findByCustomerIdAndIsDeletedFalse(Long customerId) {
        return orderRepository.findByCustomerIdAndIsDeletedFalse(customerId).stream()
                .map(OrderMapper::to).toList();
    }

    @Override
    public List<OrderResponse> findByExpertIdAndIsDeletedFalse(Long expertId) {
        return orderRepository.findByExpertIdAndIsDeletedFalse(expertId).stream()
                .map(OrderMapper::to).toList();
    }

    @Override
    public List<OrderResponse> findByExpertFieldsIdAndIsDeletedFalse(Long expertId) {
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(expertId);
        List<SubService> expertFields = expert.getExpertServiceFields();

        if (expertFields.isEmpty())
            return Collections.emptyList();

        List<Long> subServiceIds = expertFields.stream().map(SubService::getId).toList();

        List<Order> orders = orderRepository.findBySubServiceIdInAndIsDeletedFalse(subServiceIds);
        return orders.stream().map(OrderMapper::to).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findByStatusAndIsDeletedFalse(OrderStatus status) {
        return orderRepository.findByStatusAndIsDeletedFalse(status).stream()
                .map(OrderMapper::to).toList();
    }

    @Override
    public List<OrderResponse> findByServiceDateBetween(LocalDateTime startDate,
                                                        LocalDateTime endDate) {
        return orderRepository.findByServiceDateBetweenAndIsDeletedFalse(startDate, endDate)
                .stream().map(OrderMapper::to).toList();
    }

    @Override
    public void softDeleteOrderAndExpertSuggestionsAndCommentAndRateByOrderId(Long id) {
        Order order = findOrderByIdAndIsDeletedFalse(id);
        order.getExpertSuggestionList()
                .forEach(suggestion -> suggestion.setDeleted(true));

        if (order.getCustomerCommentAndRate() != null) {
            order.getCustomerCommentAndRate().setDeleted(true);
        }

        orderRepository.softDeleteById(id);
        log.info("Order with id {} deleted", id);
    }
}