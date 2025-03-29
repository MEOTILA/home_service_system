package com.example.home_service_system.controller;


import com.example.home_service_system.dto.orderDTO.OrderResponse;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> save(@Valid @RequestBody OrderSaveRequest request) {
        return ResponseEntity.ok(orderService.save(request));
    }

    @PutMapping
    public ResponseEntity<OrderResponse> update(@Valid @RequestBody OrderUpdateRequest request) {
        return ResponseEntity.ok(orderService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        orderService.softDeleteOrderAndExpertSuggestionsAndCommentAndRateByOrderId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findByIdAndIsDeletedFalse(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAllAndIsDeletedFalse());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.findByCustomerIdAndIsDeletedFalse(customerId));
    }

    @GetMapping("/expert/{expertId}")
    public ResponseEntity<List<OrderResponse>> findByExpertId(@PathVariable Long expertId) {
        return ResponseEntity.ok(orderService.findByExpertIdAndIsDeletedFalse(expertId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> findByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.findByStatusAndIsDeletedFalse(status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderResponse>> findByServiceDateBetween(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(orderService.findByServiceDateBetween(startDate, endDate));
    }
}
