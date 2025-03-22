package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.Customer;

import java.util.ArrayList;

public class CustomerMapper {
    public static CustomerResponse to(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUsername(),
                customer.getNationalId(),
                customer.getPhoneNumber(),
                customer.getBirthday(),
                customer.getEmail(),
                customer.getOrderList() != null ? customer.getOrderList().stream()
                        .map(order -> order.getId()).toList() : new ArrayList<>(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getUserStatus(),
                customer.getBalance()
        );
    }

    public static Customer fromSaveRequest(CustomerSaveRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setUsername(request.username());
        customer.setPassword(request.password());
        customer.setNationalId(request.nationalId());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setBirthday(request.birthday());
        customer.setEmail(request.email());
        customer.setBalance(0L);
        return customer;
    }

    /*public static Customer fromUpdateRequest(CustomerUpdateRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(request.id());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setUsername(request.username());
        customer.setPassword(request.password());
        customer.setNationalId(request.nationalId());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setBirthday(request.birthday());
        customer.setEmail(request.email());
        customer.setUserStatus(request.userStatus());
        customer.setBalance(request.balance());
        return customer;
    }*/

    /*public static Customer fromChangePasswordRequest(CustomerChangePasswordRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(request.id());
        customer.setPassword(request.newPassword());
        return customer;
    }*/
}

