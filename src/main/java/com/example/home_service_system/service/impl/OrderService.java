package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.orderDTO.OrderResponse;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderResponse save(@Valid OrderSaveRequest orderSaveRequest);

    OrderResponse update(@Valid OrderUpdateRequest orderUpdateRequest);

    OrderResponse findByIdAndIsDeletedFalse(Long id);

    List<OrderResponse> findAllByIsDeletedFalse();

    List<OrderResponse> findAll();

    List<OrderResponse> findByCustomerId(Long customerId);

    List<OrderResponse> findByExpertId(Long expertId);

    List<OrderResponse> findByStatus(OrderStatus status);

    List<OrderResponse> findByServiceDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    void deleteById(Long id);
}
