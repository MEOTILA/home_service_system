package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.entity.enums.UserType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.CustomerMapper;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.service.CustomerService;
import com.example.home_service_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponse save(@Valid CustomerSaveRequest request) {
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

        Customer customer = CustomerMapper.fromSaveRequest(request);
        customer.setUserStatus(UserStatus.NEW);
        userService.save(user);
        customer.setUser(user);
        customerRepository.save(customer);
        log.info("Customer with id {} saved", customer.getId());
        return CustomerMapper.to(customer);
    }

    @Override
    public CustomerResponse update(@Valid CustomerUpdateRequest request) {
        Customer updatingCustomer =
                findCustomerByIdAndIsDeletedFalse(request.id());

        User updatingUser = userService.findUserById(updatingCustomer.getUser().getId());

        if (StringUtils.hasText(request.firstName())) {
            updatingUser.setFirstName(request.firstName());
        }
        if (StringUtils.hasText(request.lastName())) {
            updatingUser.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.username())) {
            userService.usernameExists(request.username());
            updatingUser.setUsername(request.username());
        }
        if (StringUtils.hasText(request.nationalId())) {
            userService.nationalIdExists(request.nationalId());
            updatingUser.setNationalId(request.nationalId());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            userService.phoneNumberExists(request.phoneNumber());
            updatingUser.setPhoneNumber(request.phoneNumber());
        }
        if (request.birthday() != null) {
            updatingUser.setBirthday(request.birthday());
        }
        if (StringUtils.hasText(request.email())) {
            userService.emailExists(request.email());
            updatingUser.setEmail(request.email());
        }
        if (request.userStatus() != null) {
            updatingCustomer.setUserStatus(request.userStatus());
        }
        if (request.balance() != null) {
            updatingCustomer.setBalance(request.balance());
        }

        userService.update(updatingUser);
        Customer updatedCustomer = customerRepository.save(updatingCustomer);
        log.info("Customer with id {} updated", updatedCustomer.getId());
        return CustomerMapper.to(updatedCustomer);
    }


    @Override
    public CustomerResponse findByIdAndIsDeletedFalse(Long id) {
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Customer with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomerMapper.to(customer);
    }

    @Override
    public Customer findCustomerByIdAndIsDeletedFalse(Long id) {
        return customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Customer with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<CustomerResponse> findAllAndIsDeletedFalse() {
        List<Customer> foundedCustomers = customerRepository.findAllAndIsDeletedFalse();
        return foundedCustomers.stream()
                .map(CustomerMapper::to)
                .toList();
    }

    @Override
    public CustomerResponse findByUsernameAndIsDeletedFalse(String username) {
        Customer customer = customerRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Customer with username {"
                        + username + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomerMapper.to(customer);
    }

    @Override
    public void changePassword(@Valid CustomerChangePasswordRequest request) {
        Customer customer = findCustomerByIdAndIsDeletedFalse(request.id());
        User updatingUser = userService.findUserById(customer.getUser().getId());
        if (!passwordEncoder.matches(request.currentPassword(), customer.getUser().getPassword())) {
            throw new CustomApiException("Current password is incorrect!"
                    , CustomApiExceptionType.UNAUTHORIZED);
        }
        updatingUser.setPassword(request.newPassword());
        userService.changePassword(updatingUser);
        log.info("Password changed successfully for customer with id {}", request.id());
    }

    @Override
    public void softDeleteCustomerAndOrdersAndSuggestionsAndCommentAndRateById(Long id) {
        Customer customer = findCustomerByIdAndIsDeletedFalse(id);

        customer.getOrderList().forEach(order -> {
            if (order.getCustomerCommentAndRate() != null) {
                order.getCustomerCommentAndRate().setDeleted(true);
            }
            order.getExpertSuggestionList().forEach(suggestion -> suggestion.setDeleted(true));
            //order.getExpertSuggestionList().clear();
            order.setDeleted(true);
        });
        //customer.setDeleted(true);
        //customerRepository.save(customer);
        userService.softDelete(customer.getUser().getId());
        //customerRepository.softDeleteById(customer.getId());
        log.info("Customer with id {} deleted", id);
    }

    @Override
    public void softDeleteById(Long id) {
        Customer customer = findCustomerByIdAndIsDeletedFalse(id);
        userService.softDelete(customer.getUser().getId());
        log.info("Customer with id {} deleted", id);
    }
}
