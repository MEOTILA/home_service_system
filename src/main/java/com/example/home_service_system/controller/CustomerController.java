package com.example.home_service_system.controller;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public CustomerResponse save(@Valid @RequestBody CustomerSaveRequest request) {
        return customerService.save(request);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest request) {
        CustomerResponse customerResponse = customerService.update(request);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        CustomerResponse customerResponse = customerService.findByIdAndIsDeletedFalse(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerResponse> findByUsername(@PathVariable String username) {
        CustomerResponse customerResponse = customerService.findByUsernameAndIsDeletedFalse(username);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<CustomerResponse> customers = customerService.findAllAndIsDeletedFalse();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody CustomerChangePasswordRequest request) {
        customerService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteCustomer(@PathVariable Long id) {
        customerService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/full")
    public ResponseEntity<Void> softDeleteCustomerAndRelatedData(@PathVariable Long id) {
        customerService.softDeleteCustomerAndOrdersAndSuggestionsAndCommentAndRateById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public Page<CustomerResponse> filterCustomers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserStatus userStatus,
            @RequestParam(required = false) Long balance,
            @RequestParam(required = false) LocalDate createdAt,
            @RequestParam(required = false) LocalDate birthday,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return customerService.getFilteredCustomers(
                username, firstName, lastName, nationalId, phoneNumber,
                email, userStatus, balance, createdAt, birthday, page, size);
    }
}
