package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.orderDTO.OrderResponse;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.customMappers.CustomOrderMapper;
import com.example.home_service_system.repository.OrderRepository;
import com.example.home_service_system.service.CustomerService;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.OrderService;
import com.example.home_service_system.service.SubServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Order order = CustomOrderMapper.fromSaveRequest(request);

        Customer customer = customerService
                .findCustomerByIdAndIsDeletedFalse(request.customerId());
        order.setCustomer(customer);

        SubService subService = subServiceService
                .findSubServiceByIdAndIsDeletedFalse(request.subServiceId());
        order.setSubService(subService);

        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_RESPONSE);

        orderRepository.save(order);
        log.info("Order with id {} saved", order.getId());
        return CustomOrderMapper.to(order);
    }

    @Override
    public OrderResponse update(@Valid OrderUpdateRequest request) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(request.id())
                .orElseThrow(() -> new CustomApiException("Order with id {"
                        + request.id() + "} not found!", CustomApiExceptionType.NOT_FOUND));

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
        return CustomOrderMapper.to(order);
    }

    @Override
    public OrderResponse findByIdAndIsDeletedFalse(Long id) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Order with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomOrderMapper.to(order);
    }

    @Override
    public Order findOrderByIdAndIsDeletedFalse(Long id){
        return orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Order with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<OrderResponse> findAllByIsDeletedFalse() {
        return orderRepository.findAllByIsDeletedFalse().stream()
                .map(CustomOrderMapper::to).toList();    }


    @Override
    public List<OrderResponse> findByCustomerIdAndIsDeletedFalse(Long customerId) {
        return orderRepository.findByCustomerIdAndIsDeletedFalse(customerId).stream()
                .map(CustomOrderMapper::to).toList();
    }

    @Override
    public List<OrderResponse> findByExpertIdAndIsDeletedFalse(Long expertId) {
        return orderRepository.findByExpertIdAndIsDeletedFalse(expertId).stream()
                .map(CustomOrderMapper::to).toList();
    }

    @Override
    public List<OrderResponse> findByStatusAndIsDeletedFalse(OrderStatus status) {
        return orderRepository.findByStatusAndIsDeletedFalse(status).stream()
                .map(CustomOrderMapper::to).toList();
    }

    @Override
    public List<OrderResponse> findByServiceDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByServiceDateBetweenAndIsDeletedFalse(startDate, endDate).stream()
                .map(CustomOrderMapper::to).toList();
    }

    @Override
    public void softDeleteById(Long id) {
        orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Order with id {" + id + "} not found!"
                        , CustomApiExceptionType.NOT_FOUND));
        orderRepository.softDeleteById(id);
        log.info("Order with id {} deleted", id);
    }
}