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
import com.example.home_service_system.mapper.customMappers.CustomCustomerMapper;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    //private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponse save(@Valid CustomerSaveRequest customerSaveRequest) {
        Optional<Customer> optionalCustomerByUsername =
                customerRepository.findByUsername(customerSaveRequest.username());
        if (optionalCustomerByUsername.isPresent()) {
            throw new CustomApiException("Customer with username {" + customerSaveRequest.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Customer> optionalCustomerByPhoneNumber =
                customerRepository.findByPhoneNumber(customerSaveRequest.phoneNumber());
        if (optionalCustomerByPhoneNumber.isPresent()) {
            throw new CustomApiException("Customer with phone number {"
                    + customerSaveRequest.phoneNumber() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Customer> optionalCustomerByNationalId =
                customerRepository.findByNationalId(customerSaveRequest.nationalId());
        if (optionalCustomerByNationalId.isPresent()) {
            throw new CustomApiException("Customer with national ID {"
                    + customerSaveRequest.nationalId() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Customer> optionalCustomerByEmail =
                customerRepository.findByEmail(customerSaveRequest.email());
        if (optionalCustomerByEmail.isPresent()) {
            throw new CustomApiException("Customer with email {"
                    + customerSaveRequest.email() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        String hashedPassword = passwordEncoder.encode(customerSaveRequest.password());
        Customer customer = CustomCustomerMapper.fromSaveRequest(customerSaveRequest);
        customer.setPassword(hashedPassword);
        customer.setUserStatus(UserStatus.NEW);
        customerRepository.save(customer);
        log.info("Customer with id {} saved", customer.getId());
        return CustomCustomerMapper.to(customer);
    }

    @Override
    public CustomerResponse update(@Valid CustomerUpdateRequest customerUpdateRequest) {
        Customer updatingCustomer = customerRepository.findByIdAndIsDeletedFalse(customerUpdateRequest.id())
                .orElseThrow(() -> new CustomApiException("Customer with id {"
                        + customerUpdateRequest.id() + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        if (StringUtils.hasText(customerUpdateRequest.firstName())) {
            updatingCustomer.setFirstName(customerUpdateRequest.firstName());
        }
        if (StringUtils.hasText(customerUpdateRequest.lastName())) {
            updatingCustomer.setLastName(customerUpdateRequest.lastName());
        }
        if (StringUtils.hasText(customerUpdateRequest.username())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByUsername(customerUpdateRequest.username());
            if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with username {"
                        + customerUpdateRequest.username() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setUsername(customerUpdateRequest.username());
        }
        if (StringUtils.hasText(customerUpdateRequest.password())) {
            String hashedPassword = passwordEncoder.encode(customerUpdateRequest.password());
            updatingCustomer.setPassword(hashedPassword);
        }
        if (StringUtils.hasText(customerUpdateRequest.nationalId())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByNationalId(customerUpdateRequest.nationalId());
            if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with national ID {"
                        + customerUpdateRequest.nationalId() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setNationalId(customerUpdateRequest.nationalId());
        }
        if (StringUtils.hasText(customerUpdateRequest.phoneNumber())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByPhoneNumber(customerUpdateRequest.phoneNumber());
            if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with phone number {"
                        + customerUpdateRequest.phoneNumber() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setPhoneNumber(customerUpdateRequest.phoneNumber());
        }
        if (customerUpdateRequest.birthday() != null) {
            updatingCustomer.setBirthday(customerUpdateRequest.birthday());
        }
        if (StringUtils.hasText(customerUpdateRequest.email())) {
            Optional<Customer> existingCustomer = customerRepository
                    .findByEmail(customerUpdateRequest.email());
            if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(updatingCustomer.getId())) {
                throw new CustomApiException("Customer with email {"
                        + customerUpdateRequest.email() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingCustomer.setEmail(customerUpdateRequest.email());
        }
        if (customerUpdateRequest.userStatus() != null) {
            updatingCustomer.setUserStatus(customerUpdateRequest.userStatus());
        }
        if (customerUpdateRequest.balance() != null) {
            updatingCustomer.setBalance(customerUpdateRequest.balance());
        }

        Customer updatedCustomer = customerRepository.save(updatingCustomer);
        log.info("Customer with id {} updated", updatedCustomer.getId());
        return CustomCustomerMapper.to(updatedCustomer);
    }


    @Override
    public CustomerResponse findByIdAndIsDeletedFalse(Long id) {
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Customer with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return CustomCustomerMapper.to(customer);
    }

    @Override
    public Customer findCustomerByIdAndIsDeletedFalse(Long id) {
        return customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Customer with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<CustomerResponse> findAllByIsDeletedFalse() {
        List<Customer> foundedCustomers = customerRepository.findAllByIsDeletedFalse();
        return foundedCustomers.stream()
                .map(CustomCustomerMapper::to)
                .toList();
    }

    @Override
    public CustomerResponse findByUsernameAndIsDeletedFalse(String username) {
        Customer customer = customerRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Customer with username {" + username + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return CustomCustomerMapper.to(customer);
    }

    @Override
    public void softDeleteById(Long id) {
        customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Customer with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        customerRepository.softDeleteById(id);
        log.info("Customer with id {} deleted", id);
    }

    @Override
    public void changePassword(Long id, @Valid CustomerChangePasswordRequest request) {
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Customer with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));

        if (!passwordEncoder.matches(request.currentPassword(), customer.getPassword())) {
            throw new CustomApiException("Current password is incorrect!", CustomApiExceptionType.UNAUTHORIZED);
        }
        String hashedNewPassword = passwordEncoder.encode(request.newPassword());
        customer.setPassword(hashedNewPassword);
        customerRepository.save(customer);
        log.info("Password changed successfully for customer with id {}", id);
    }
}
