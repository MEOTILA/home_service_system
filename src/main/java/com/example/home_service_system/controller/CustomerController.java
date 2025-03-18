package com.example.home_service_system.controller;

import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
