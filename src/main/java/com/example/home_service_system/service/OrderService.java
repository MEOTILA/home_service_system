package com.example.home_service_system.service;

import com.example.home_service_system.dto.orderDTO.*;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    FilteredOrderResponse findAllOrders(OrderFilterDTO filter);

    FilteredOrderResponse findAllOrdersPageable(OrderFilterDTO filter, Pageable pageable);
}
