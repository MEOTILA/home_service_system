package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.CustomerMapper;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.service.CustomerService;
import com.example.home_service_system.specification.CustomerSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponse save(@Valid CustomerSaveRequest request) {
        Optional<Customer> optionalCustomerByUsername =
                customerRepository.findByUsername(request.username());
        if (optionalCustomerByUsername.isPresent()) {
            throw new CustomApiException("Customer with username {" + request.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Customer> optionalCustomerByPhoneNumber =
                customerRepository.findByPhoneNumber(request.phoneNumber());
        if (optionalCustomerByPhoneNumber.isPresent()) {
            throw new CustomApiException("Customer with phone number {"
                    + request.phoneNumber() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Customer> optionalCustomerByNationalId =
                customerRepository.findByNationalId(request.nationalId());
        if (optionalCustomerByNationalId.isPresent()) {
            throw new CustomApiException("Customer with national ID {"
                    + request.nationalId() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Customer> optionalCustomerByEmail =
                customerRepository.findByEmail(request.email());
        if (optionalCustomerByEmail.isPresent()) {
            throw new CustomApiException("Customer with email {"
                    + request.email() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        String hashedPassword = passwordEncoder.encode(request.password());
        Customer customer = CustomerMapper.fromSaveRequest(request);
        customer.setPassword(hashedPassword);
        customer.setUserStatus(UserStatus.NEW);
        customerRepository.save(customer);
        log.info("Customer with id {} saved", customer.getId());
        return CustomerMapper.to(customer);
    }

    @Override
    public CustomerResponse update(@Valid CustomerUpdateRequest request) {
        Customer updatingCustomer =
                findCustomerByIdAndIsDeletedFalse(request.id());

        if (StringUtils.hasText(request.firstName())) {
            updatingCustomer.setFirstName(request.firstName());
        }
        if (StringUtils.hasText(request.lastName())) {
            updatingCustomer.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.username())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByUsername(request.username());
            if (existingCustomer.isPresent() && !existingCustomer.get()
                    .getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with username {"
                        + request.username() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setUsername(request.username());
        }
        if (StringUtils.hasText(request.password())) {
            String hashedPassword = passwordEncoder.encode(request.password());
            updatingCustomer.setPassword(hashedPassword);
        }
        if (StringUtils.hasText(request.nationalId())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByNationalId(request.nationalId());
            if (existingCustomer.isPresent() && !existingCustomer.get()
                    .getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with national ID {"
                        + request.nationalId() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setNationalId(request.nationalId());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByPhoneNumber(request.phoneNumber());
            if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with phone number {"
                        + request.phoneNumber() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setPhoneNumber(request.phoneNumber());
        }
        if (request.birthday() != null) {
            updatingCustomer.setBirthday(request.birthday());
        }
        if (StringUtils.hasText(request.email())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByEmail(request.email());
            if (existingCustomer.isPresent() && !existingCustomer.get()
                    .getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with email {"
                        + request.email() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setEmail(request.email());
        }
        if (request.userStatus() != null) {
            updatingCustomer.setUserStatus(request.userStatus());
        }
        if (request.balance() != null) {
            updatingCustomer.setBalance(request.balance());
        }

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
    public List<CustomerResponse> findAllByIsDeletedFalse() {
        List<Customer> foundedCustomers = customerRepository.findAllByIsDeletedFalse();
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

        if (!passwordEncoder.matches(request.currentPassword(), customer.getPassword())) {
            throw new CustomApiException("Current password is incorrect!"
                    , CustomApiExceptionType.UNAUTHORIZED);
        }
        String hashedNewPassword = passwordEncoder.encode(request.newPassword());
        customer.setPassword(hashedNewPassword);
        customerRepository.save(customer);
        log.info("Password changed successfully for customer with id {}", request.id());
    }

    @Override
    public void softDeleteCustomerAndOrdesrAndSuggestionsAndCommentAndRateById(Long id) {
       Customer customer = findCustomerByIdAndIsDeletedFalse(id);

        customer.getOrderList().forEach(order -> {
            if (order.getCustomerCommentAndRate() != null) {
                order.getCustomerCommentAndRate().setDeleted(true);
            }
            if (order.getExpert() != null) {
                order.getExpert().getOrderList().remove(order);
            }
            order.getExpertSuggestionList().forEach(suggestion -> suggestion.setDeleted(true));
            //order.getExpertSuggestionList().clear();
            order.setDeleted(true);
        });
        customer.setDeleted(true);
        customerRepository.save(customer);
        log.info("Customer with id {} deleted", id);
    }

    @Override
    public void softDeleteById(Long id) {
        findCustomerByIdAndIsDeletedFalse(id);
        customerRepository.softDeleteById(id);
        log.info("Customer with id {} deleted", id);
    }

    @Override
    public Page<CustomerResponse> getFilteredCustomers(
            String firstName, String lastName, String username,
            String nationalId, String phoneNumber, String email,
            UserStatus userStatus, Long balance, LocalDate createdAt,
            LocalDate birthday, int page, int size) {

        Specification<Customer> spec = CustomerSpecification.filterCustomers(
                firstName, lastName, username, nationalId, phoneNumber,
                email, userStatus, balance, createdAt, birthday
        );

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);

        return customerPage.map(CustomerMapper::to);
    }
}
