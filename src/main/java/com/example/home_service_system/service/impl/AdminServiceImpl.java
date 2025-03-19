package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.customMappers.CustomAdminMapper;
import com.example.home_service_system.repository.AdminRepository;
import com.example.home_service_system.service.AdminService;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse save(@Valid AdminSaveRequest request) {
        Optional<Admin> optionalAdminByUsername =
                adminRepository.findByUsername(request.username());
        if (optionalAdminByUsername.isPresent()) {
            throw new CustomApiException("Admin with username {"
                    + request.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Admin> optionalAdminByPhoneNumber =
                adminRepository.findByPhoneNumber(request.phoneNumber());
        if (optionalAdminByPhoneNumber.isPresent()) {
            throw new CustomApiException("Admin with phone number {"
                    + request.phoneNumber() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Admin> optionalAdminByNationalId =
                adminRepository.findByNationalId(request.nationalId());
        if (optionalAdminByNationalId.isPresent()) {
            throw new CustomApiException("Admin with national ID {"
                    + request.nationalId() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Admin> optionalAdminByEmail =
                adminRepository.findByEmail(request.email());
        if (optionalAdminByEmail.isPresent()) {
            throw new CustomApiException("Admin with email {"
                    + request.email() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        String hashedPassword = passwordEncoder.encode(request.password());
        Admin admin = CustomAdminMapper.fromSaveRequest(request);
        admin.setPassword(hashedPassword);
        adminRepository.save(admin);
        log.info("Admin with id {} saved", admin.getId());
        return CustomAdminMapper.to(admin);
    }

    @Override
    public AdminResponse update(@Valid AdminUpdateRequest request) {
        Admin updatingAdmin = findAdminByIdAndIsDeletedFalse(request.id());

        if (StringUtils.hasText(request.firstName())) {
            updatingAdmin.setFirstName(request.firstName());
        }
        if (StringUtils.hasText(request.lastName())) {
            updatingAdmin.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.username())) {
            Optional<Admin> existingAdmin = adminRepository
                    .findByUsername(request.username());
            if (existingAdmin.isPresent() && !existingAdmin.get()
                    .getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with username {"
                        + request.username() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setUsername(request.username());
        }
        if (StringUtils.hasText(request.password())) {
            String hashedPassword = passwordEncoder.encode(request.password());
            updatingAdmin.setPassword(hashedPassword);
        }
        if (StringUtils.hasText(request.nationalId())) {
            Optional<Admin> existingAdmin = adminRepository
                    .findByNationalId(request.nationalId());
            if (existingAdmin.isPresent() && !existingAdmin.get()
                    .getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with national ID {"
                        + request.nationalId() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setNationalId(request.nationalId());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            Optional<Admin> existingAdmin = adminRepository
                    .findByPhoneNumber(request.phoneNumber());
            if (existingAdmin.isPresent() && !existingAdmin.get()
                    .getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with phone number {"
                        + request.phoneNumber() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setPhoneNumber(request.phoneNumber());
        }
        if (request.birthday() != null) {
            updatingAdmin.setBirthday(request.birthday());
        }
        if (StringUtils.hasText(request.email())) {
            Optional<Admin> existingAdmin = adminRepository
                    .findByEmail(request.email());
            if (existingAdmin.isPresent() && !existingAdmin.get()
                    .getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with email {"
                        + request.email() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setEmail(request.email());
        }
        Admin updatedAdmin = adminRepository.save(updatingAdmin);
        log.info("Admin with id {} updated", updatedAdmin.getId());
        return CustomAdminMapper.to(updatedAdmin);
    }

    @Override
    public AdminResponse findByIdAndIsDeletedFalse(Long id) {
        Admin admin = adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomAdminMapper.to(admin);
    }

    @Override
    public Admin findAdminByIdAndIsDeletedFalse(Long id) {
        return adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));

    }

    @Override
    public List<AdminResponse> findAllByIsDeletedFalse() {
        List<Admin> foundedAdmins = adminRepository.findAllByIsDeletedFalse();
        return foundedAdmins.stream().map(CustomAdminMapper::to).toList();
    }

    @Override
    public AdminResponse findByUsernameAndIsDeletedFalse(String username) {
        Admin admin = adminRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Admin with username {"
                        + username + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return CustomAdminMapper.to(admin);
    }

    @Override
    public void changePassword(@Valid AdminChangePasswordRequest request) {
        Admin admin = findAdminByIdAndIsDeletedFalse(request.id());

        if (!passwordEncoder.matches(request.currentPassword(), admin.getPassword())) {
            throw new CustomApiException("Current password is incorrect!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        String hashedNewPassword = passwordEncoder.encode(request.newPassword());
        admin.setPassword(hashedNewPassword);
        adminRepository.save(admin);
        log.info("Password changed successfully for admin with id {}", request.id());
    }

    @Override
    public void softDeleteById(Long id) {
        findAdminByIdAndIsDeletedFalse(id);
        adminRepository.softDeleteById(id);
        log.info("Admin with id {} deleted", id);
    }

}
