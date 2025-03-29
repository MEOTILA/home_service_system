package com.example.home_service_system.controller;

import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateResponse;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateSaveRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateUpdateRequest;
import com.example.home_service_system.service.CustomerCommentAndRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-comments")
@RequiredArgsConstructor
@Validated
public class CustomerCommentAndRateController {

    private final CustomerCommentAndRateService customerCommentAndRateService;

    @PostMapping
    public ResponseEntity<CustomerCommentAndRateResponse> save(
            @Valid @RequestBody CustomerCommentAndRateSaveRequest request) {
        return ResponseEntity.ok(customerCommentAndRateService.save(request));
    }

    @PutMapping
    public ResponseEntity<CustomerCommentAndRateResponse> update(
            @Valid @RequestBody CustomerCommentAndRateUpdateRequest request) {
        return ResponseEntity.ok(customerCommentAndRateService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerCommentAndRateResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerCommentAndRateService.findByIdAndIsDeletedFalse(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerCommentAndRateResponse>> findAll() {
        return ResponseEntity.ok(customerCommentAndRateService.findAllAndIsDeletedFalse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        customerCommentAndRateService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }
}
