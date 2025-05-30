package com.example.home_service_system.web;


import com.example.home_service_system.dto.orderDTO.*;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
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

    @PutMapping("/acceptingOrder")
    public ResponseEntity<OrderResponse> acceptingAnExpertForOrder(@RequestParam Long orderId, Long expertId) {
        return ResponseEntity.ok(orderService.acceptingAnExpertForOrder(orderId, expertId));
    }

    @PutMapping("/service-starter")
    public ResponseEntity<OrderResponse> serviceStarter(@RequestParam Long orderId, Long expertId) {
        return ResponseEntity.ok(orderService.serviceStarter(orderId,expertId ));
    }

    @PutMapping("/service-completer")
    public ResponseEntity<OrderResponse> serviceCompleter(@RequestParam Long orderId, Long expertId) {
        return ResponseEntity.ok(orderService.serviceCompleter(orderId, expertId ));
    }

    /*@PutMapping("/payment")
    public ResponseEntity<OrderResponse> payment(@Valid @RequestBody OrderPaymentRequest request) {
        return ResponseEntity.ok(orderService.payment(request));
    }*/

    @PutMapping("/payment")
    public ResponseEntity<OrderResponse> payment(@Valid @RequestBody OrderPaymentRequest request) {
        OrderResponse response = orderService.payment(request);
        return ResponseEntity.ok(response);
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

    @GetMapping("/expert/exFields/{expertId}")
    public ResponseEntity<List<OrderResponse>> findByExpertFields(@PathVariable Long expertId) {
        return ResponseEntity.ok(orderService.findByExpertFieldsIdAndIsDeletedFalse(expertId));
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

    @GetMapping("/getAllOrders")
    public ResponseEntity<FilteredOrderResponse> getAllOrders(
            @Valid OrderFilterDTO filter) {
        return ResponseEntity.ok(orderService.findAllOrders(filter));
    }

    @GetMapping("/paginated")
    public ResponseEntity<FilteredOrderResponse> getOrdersPaginated(
            @Valid OrderFilterDTO filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(orderService.findAllOrdersPageable(filter, pageable));
    }
}
