package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.entity.enums.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {
    public static CustomerResponse to(Customer customer) {
        if (customer == null || customer.getUser() == null) {
            return null;
        }

        User user = customer.getUser();
        List<Long> orderIds = customer.getOrderList() != null ?
                customer.getOrderList().stream()
                        .map(order -> order.getId())
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return new CustomerResponse(
                customer.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getNationalId(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getEmail(),
                orderIds,
                user.getCreatedAt(),
                user.getUpdatedAt(),
                customer.getUser().getUserStatus(),
                customer.getBalance()
        );
    }
    /*public static CustomerResponse to(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getUser().getFirstName(),
                customer.getUser().getLastName(),
                customer.getUser().getUsername(),
                customer.getUser().getNationalId(),
                customer.getUser().getPhoneNumber(),
                customer.getUser().getBirthday(),
                customer.getUser().getEmail(),
                customer.getOrderList() != null ? customer.getOrderList().stream()
                        .map(order -> order.getId()).toList() : new ArrayList<>(),
                customer.getUser().getCreatedAt(),
                customer.getUser().getUpdatedAt(),
                customer.getUserStatus(),
                customer.getBalance()
        );
    }*/

    /*public static Customer fromSaveRequest(CustomerSaveRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.getUser().setFirstName(request.firstName());
        customer.getUser().setLastName(request.lastName());
        customer.getUser().setUsername(request.username());
        customer.getUser().setPassword(request.password());
        customer.getUser().setNationalId(request.nationalId());
        customer.getUser().setPhoneNumber(request.phoneNumber());
        customer.getUser().setBirthday(request.birthday());
        customer.getUser().setEmail(request.email());
        customer.setBalance(0L);
        return customer;
    }*/
    public static Customer fromSaveRequest(CustomerSaveRequest request) {
        if (request == null) {
            return null;
        }

        // Create User first
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setNationalId(request.nationalId());
        user.setPhoneNumber(request.phoneNumber());
        user.setBirthday(request.birthday());
        user.setEmail(request.email());
        user.setUserType(UserType.CUSTOMER);

        // Create Customer and establish relationship
        Customer customer = new Customer();
        customer.setUser(user);
        //customer.setUserStatus(UserStatus.NEW); // Set default status
        customer.setBalance(0L);

        // Establish bidirectional relationship
        //user.setCustomer(customer);

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

