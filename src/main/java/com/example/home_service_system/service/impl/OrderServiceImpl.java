package com.example.home_service_system.service.impl;

import com.example.home_service_system.config.CaptchaService;
import com.example.home_service_system.dto.orderDTO.*;
import com.example.home_service_system.entity.*;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.entity.enums.PaymentType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.OrderMapper;
import com.example.home_service_system.repository.OrderRepository;
import com.example.home_service_system.service.*;
import com.example.home_service_system.specification.OrderSpecification;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final CaptchaService captchaService;

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
        order.setPaymentType(PaymentType.BY_BALANCE);

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
        if (request.paymentType() != null) {
            order.setPaymentType(request.paymentType());
        }
        orderRepository.save(order);
        log.info("Order with id {} updated", order.getId());
        return OrderMapper.to(order);
    }

    @Override
    public OrderResponse acceptingAnExpertForOrder(Long orderId, Long expertId) {
        Order order = findOrderByIdAndIsDeletedFalse(orderId);
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(expertId);
        List<ExpertSuggestion> expertSuggestionList = order.getExpertSuggestionList();

        if (order.getExpert() != null ||
                !order.getStatus().equals(OrderStatus.WAITING_FOR_CUSTOMER_TO_ACCEPT)) {
            throw new CustomApiException("Order with ID " + orderId +
                    " is already has an expert!",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        boolean isExpertSuggested = expertSuggestionList.stream()
                .anyMatch(suggestion -> suggestion.getExpert().getId().equals(expertId));

        if (!isExpertSuggested) {
            throw new CustomApiException("Expert with ID " + expertId +
                    " is not in the suggestion list for this order!",
                    CustomApiExceptionType.NOT_FOUND);
        }
        ExpertSuggestion expertSuggestion = expertSuggestionList.stream()
                .filter(suggestion -> suggestion.getExpert().getId().equals(expertId))
                .findFirst()
                .orElseThrow(() -> new CustomApiException("Expert with ID " + expertId +
                        " is not in the suggestion list for this order!",
                        CustomApiExceptionType.NOT_FOUND));

        order.setExpert(expert);
        order.setCustomerOfferedCost(expertSuggestion.getExpertOfferedCost());
        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_ARRIVE);
        orderRepository.save(order);
        log.info("Order with id {} is assigned to expert with id {}",
                order.getId(), expert.getId());
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

        List<OrderResponse> filteredOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.WAITING_FOR_EXPERT_TO_RESPONSE)
                .map(OrderMapper::to)
                .collect(Collectors.toList());

        return filteredOrders;
    }

    @Override
    public List<OrderResponse> findByStatusAndIsDeletedFalse(OrderStatus status) {
        return orderRepository.findByStatusAndIsDeletedFalse(status).stream()
                .map(OrderMapper::to).toList();
    }

    @Override
    public OrderResponse serviceStarter(Long orderId, Long expertId) {
        expertService.findExpertByIdAndIsDeletedFalse(expertId);
        Order order = findOrderByIdAndIsDeletedFalse(orderId);
        if (!order.getExpert().getId().equals(expertId)) {
            throw new CustomApiException("Expert with ID {"
                    + expertId + "} is not for the order!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        if (!order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_TO_ARRIVE)) {
            throw new CustomApiException("Expert with ID is not arrived yet!",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        order.setStatus(OrderStatus.SERVICE_IS_STARTED);
        orderRepository.save(order);
        return OrderMapper.to(order);
    }

    @Override
    public OrderResponse serviceCompleter(Long orderId, Long expertId) {
        expertService.findExpertByIdAndIsDeletedFalse(expertId);
        Order order = findOrderByIdAndIsDeletedFalse(orderId);
        if (!order.getExpert().getId().equals(expertId)) {
            throw new CustomApiException("Expert with ID {"
                    + expertId + "} is not for the order!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        if (!order.getStatus().equals(OrderStatus.SERVICE_IS_STARTED)) {
            throw new CustomApiException("Order with ID {"
                    + orderId + "} is finished or is not started yet!",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        order.setStatus(OrderStatus.SERVICE_IS_DONE);
        orderRepository.save(order);
        log.info("Service for order with id {" + orderId + "} is done!");
        return OrderMapper.to(order);
    }

    @Override
    public OrderResponse payment(OrderPaymentRequest request) {
        Order order = findOrderByIdAndIsDeletedFalse(request.id());
        Customer customer = customerService.findCustomerByIdAndIsDeletedFalse(request.customerId());
        Expert expert = expertService.findExpertByIdAndIsDeletedFalse(order.getExpert().getId());

        if (!order.getCustomer().getId().equals(request.customerId())) {
            throw new CustomApiException("Customer with ID {" + request.customerId()
                    + "} is not for the order!", CustomApiExceptionType.UNAUTHORIZED);
        }

        if (!order.getStatus().equals(OrderStatus.SERVICE_IS_DONE)) {
            throw new CustomApiException("Order with ID {" + order.getId()
                    + "} is paid or is not finished yet!", CustomApiExceptionType.BAD_REQUEST);
        }

        if (customer.getBalance() >= order.getCustomerOfferedCost()) {
            return payByBalance(order, customer, expert);
        } else {
            if (request.cardNumber() == null || request.cardNumber().trim().isEmpty()) {
                throw new CustomApiException("Card number is required for credit card payments!",
                        CustomApiExceptionType.BAD_REQUEST);
            }
            if (request.captchaToken() == null || request.captchaToken().trim().isEmpty()) {
                throw new CustomApiException("CAPTCHA token is required for credit card payments!",
                        CustomApiExceptionType.BAD_REQUEST);
            }

            captchaService.verify(request.captchaToken());

            return payByCreditCard(order, customer, expert);
        }
    }

    private OrderResponse payByBalance(Order order, Customer customer, Expert expert) {
        Long orderCost = order.getCustomerOfferedCost();
        Long seventyPercent = (long) (orderCost * 0.7);

        order.setPaymentType(PaymentType.BY_BALANCE);
        customer.setBalance(customer.getBalance() - orderCost);
        expert.setBalance(expert.getBalance() + seventyPercent);
        order.setStatus(OrderStatus.SERVICE_IS_PAID);

        orderRepository.save(order);

        log.info("Payment by customer balance is successful!");
        return OrderMapper.to(order);
    }

    private OrderResponse payByCreditCard(Order order, Customer customer, Expert expert) {
        Long orderCost = order.getCustomerOfferedCost();
        Long seventyPercent = (long) (orderCost * 0.7);

        order.setPaymentType(PaymentType.BY_CREDIT_CARD);
        expert.setBalance(expert.getBalance() + seventyPercent);
        order.setStatus(OrderStatus.SERVICE_IS_PAID);

        orderRepository.save(order);

        log.info("Payment by credit card is successful!");
        return OrderMapper.to(order);
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

    @Override
    public FilteredOrderResponse findAllOrders(OrderFilterDTO filter) {
        List<Order> orders = orderRepository.findAll(buildSpecification(filter));
        return OrderMapper.toFilteredOrderResponse(orders, filter);
    }

    @Override
    public FilteredOrderResponse findAllOrdersPageable(OrderFilterDTO filter, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(buildSpecification(filter), pageable);
        return OrderMapper.toFilteredOrderResponse(orderPage, filter);
    }

    private Specification<Order> buildSpecification(OrderFilterDTO filter) {
        return Specification.where(OrderSpecification.isNotDeleted())
                .and(OrderSpecification.hasSubServiceId(filter.getSubServiceId()))
                .and(OrderSpecification.hasCustomerId(filter.getCustomerId()))
                .and(OrderSpecification.hasExpertId(filter.getExpertId()))
                .and(OrderSpecification.hasMinCustomerOfferedCost(filter.getMinCost()))
                .and(OrderSpecification.hasMaxCustomerOfferedCost(filter.getMaxCost()))
                .and(OrderSpecification.hasCustomerDescription(filter.getDescription()))
                .and(OrderSpecification.serviceDateBetween(
                        filter.getServiceStartDate(),
                        filter.getServiceEndDate()))
                .and(OrderSpecification.hasAddress(filter.getAddress()))
                .and(OrderSpecification.hasStatus(filter.getStatus()))
                .and(OrderSpecification.hasPaymentType(filter.getPaymentType()))
                .and(OrderSpecification.createdAtAfter(filter.getCreatedAfter()))
                .and(OrderSpecification.createdAtBefore(filter.getCreatedBefore()))
                .and(OrderSpecification.hasMainServiceId(filter.getMainServiceId()))
                .and(filter.getHasComment() != null ?
                        OrderSpecification.hasCustomerComment(filter.getHasComment()) :
                        null);
    }
}