package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.orderDTO.OrderResponse;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponse to(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getSubService() != null ? order.getSubService().getId() : null,
                order.getCustomer() != null ? order.getCustomer().getId() : null,
                order.getExpert() != null ? order.getExpert().getId() : null,
                order.getCustomerOfferedCost(),
                order.getCustomerDescription(),
                order.getPaymentType(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getServiceDate(),
                order.getAddress(),
                order.getStatus(),
                order.getExpertSuggestionList().stream()
                        .map(expertSuggestion -> expertSuggestion.getId())
                        .collect(Collectors.toList()),
                //order.getCustomerCommentAndRate(),
                order.getCustomerCommentAndRate() != null ? order.getCustomerCommentAndRate().getId() : null
        );
    }

    public static Order fromSaveRequest(OrderSaveRequest request) {
        Order order = new Order();
        order.setCustomerOfferedCost(request.customerOfferedCost());
        order.setCustomerDescription(request.customerDescription());
        order.setServiceDate(request.serviceDate());
        order.setAddress(request.address());
        //order.setStatus(request.status());
        return order;
    }

   /* public static Order fromUpdateRequest(OrderUpdateRequest request) {
        Order order = new Order();
        order.setId(request.id());
        order.setCustomerOfferedCost(request.customerOfferedCost());
        order.setCustomerDescription(request.customerDescription());
        order.setServiceDate(request.serviceDate());
        order.setAddress(request.address());
        order.setStatus(request.status());
        return order;
    }*/

    public static OrderUpdateRequest toUpdateRequest(Order order) {
        return new OrderUpdateRequest(
                order.getId(),
                order.getSubService().getId(),
                order.getCustomer().getId(),
//                order.getExpert().getId(),
                order.getExpert() != null ? order.getExpert().getId() : null,
                order.getCustomerOfferedCost(),
                order.getCustomerDescription(),
                order.getServiceDate(),
                order.getAddress(),
                order.getPaymentType(),
                order.getStatus(),
                null
                /*order.getExpertSuggestionList().stream().map(e -> e.getId())
                        .collect(Collectors.toList()),
                order.getCustomerCommentAndRate()*/
        );
    }
}

