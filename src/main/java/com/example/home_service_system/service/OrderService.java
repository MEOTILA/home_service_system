package com.example.home_service_system.service;

import com.example.home_service_system.dto.orderDTO.OrderPaymentRequest;
import com.example.home_service_system.dto.orderDTO.OrderResponse;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderResponse save(@Valid OrderSaveRequest orderSaveRequest);

    OrderResponse update(@Valid OrderUpdateRequest orderUpdateRequest);

    OrderResponse acceptingAnExpertForOrder(Long orderId, Long expertId);

    OrderResponse findByIdAndIsDeletedFalse(Long id);

    Order findOrderByIdAndIsDeletedFalse(Long id);

    List<OrderResponse> findAllAndIsDeletedFalse();

    List<OrderResponse> findByCustomerIdAndIsDeletedFalse(Long customerId);

    List<OrderResponse> findByExpertIdAndIsDeletedFalse(Long expertId);

    List<OrderResponse> findByExpertFieldsIdAndIsDeletedFalse(Long expertId);

    List<OrderResponse> findByStatusAndIsDeletedFalse(OrderStatus status);

    OrderResponse serviceStarter(Long orderId, Long expertId);

    OrderResponse serviceCompleter(Long orderId, Long expertId);

    OrderResponse payment(OrderPaymentRequest orderPaymentRequest);

    List<OrderResponse> findByServiceDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    void softDeleteOrderAndExpertSuggestionsAndCommentAndRateByOrderId(Long id);
}
