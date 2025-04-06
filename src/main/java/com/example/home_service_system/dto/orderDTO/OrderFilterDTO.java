package com.example.home_service_system.dto.orderDTO;

import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilterDTO {
    private Long subServiceId;
    private Long customerId;
    private Long expertId;
    private Long minCost;
    private Long maxCost;
    private String description;
    private LocalDateTime serviceStartDate;
    private LocalDateTime serviceEndDate;
    private String address;
    private OrderStatus status;
    private PaymentType paymentType;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private Boolean hasComment;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";

}
