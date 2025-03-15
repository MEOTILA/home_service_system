package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.AdminMapper;
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
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse save(@Valid AdminSaveRequest adminSaveRequest) {
        Optional<Admin> optionalAdminByUsername =
                adminRepository.findByUsername(adminSaveRequest.username());
        if (optionalAdminByUsername.isPresent()) {
            throw new CustomApiException("Admin with username {"
                    + adminSaveRequest.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Admin> optionalAdminByPhoneNumber =
                adminRepository.findByPhoneNumber(adminSaveRequest.phoneNumber());
        if (optionalAdminByPhoneNumber.isPresent()) {
            throw new CustomApiException("Admin with phone number {"
                    + adminSaveRequest.phoneNumber() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Admin> optionalAdminByNationalId =
                adminRepository.findByNationalId(adminSaveRequest.nationalId());
        if (optionalAdminByNationalId.isPresent()) {
            throw new CustomApiException("Admin with national ID {"
                    + adminSaveRequest.nationalId() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Admin> optionalAdminByEmail =
                adminRepository.findByEmail(adminSaveRequest.email());
        if (optionalAdminByEmail.isPresent()) {
            throw new CustomApiException("Admin with email {"
                    + adminSaveRequest.email() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        String hashedPassword = passwordEncoder.encode(adminSaveRequest.password());
        Admin admin = adminMapper.fromSaveRequest(adminSaveRequest);
        admin.setPassword(hashedPassword);
        adminRepository.save(admin);
        log.info("Admin with id {} saved", admin.getId());
        return adminMapper.to(admin);
    }

    @Override
    public AdminResponse update(@Valid AdminUpdateRequest adminUpdateRequest) {
        Admin updatingAdmin = adminRepository.findByIdAndIsDeletedFalse(adminUpdateRequest.id())
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + adminUpdateRequest.id() + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        if (StringUtils.hasText(adminUpdateRequest.firstName())) {
            updatingAdmin.setFirstName(adminUpdateRequest.firstName());
        }
        if (StringUtils.hasText(adminUpdateRequest.lastName())) {
            updatingAdmin.setLastName(adminUpdateRequest.lastName());
        }
        if (StringUtils.hasText(adminUpdateRequest.username())) {
            Optional<Admin> existingAdmin = adminRepository.findByUsername(adminUpdateRequest.username());
            if (existingAdmin.isPresent() && !existingAdmin.get().getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with username {"
                        + adminUpdateRequest.username() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setUsername(adminUpdateRequest.username());
        }
        if (StringUtils.hasText(adminUpdateRequest.password())) {
            String hashedPassword = passwordEncoder.encode(adminUpdateRequest.password());
            updatingAdmin.setPassword(hashedPassword);
        }
        if (StringUtils.hasText(adminUpdateRequest.nationalId())) {
            Optional<Admin> existingAdmin = adminRepository.findByNationalId(adminUpdateRequest.nationalId());
            if (existingAdmin.isPresent() && !existingAdmin.get().getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with national ID {"
                        + adminUpdateRequest.nationalId() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setNationalId(adminUpdateRequest.nationalId());
        }
        if (StringUtils.hasText(adminUpdateRequest.phoneNumber())) {
            Optional<Admin> existingAdmin = adminRepository.findByPhoneNumber(adminUpdateRequest.phoneNumber());
            if (existingAdmin.isPresent() && !existingAdmin.get().getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with phone number {"
                        + adminUpdateRequest.phoneNumber() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setPhoneNumber(adminUpdateRequest.phoneNumber());
        }
        if (adminUpdateRequest.birthday() != null) {
            updatingAdmin.setBirthday(adminUpdateRequest.birthday());
        }
        if (StringUtils.hasText(adminUpdateRequest.email())) {
            Optional<Admin> existingAdmin = adminRepository.findByEmail(adminUpdateRequest.email());
            if (existingAdmin.isPresent() && !existingAdmin.get().getId().equals(updatingAdmin.getId())) {
                throw new CustomApiException("Admin with email {"
                        + adminUpdateRequest.email() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingAdmin.setEmail(adminUpdateRequest.email());
        }
        Admin updatedAdmin = adminRepository.save(updatingAdmin);
        log.info("Admin with id {} updated", updatedAdmin.getId());
        return adminMapper.to(updatedAdmin);
    }

    @Override
    public AdminResponse findByIdAndIsDeletedFalse(Long id) {
        Admin admin = adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return adminMapper.to(admin);
    }

    @Override
    public List<AdminResponse> findAll() {
        List<Admin> foundedAdmins = adminRepository.findAllByIsDeletedFalse();
        return foundedAdmins.stream()
                .map(adminMapper::to)
                .toList();
    }

    @Override
    public AdminResponse findByUsername(String username) {
        Admin admin = adminRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Admin with username {"
                        + username + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return adminMapper.to(admin);
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        adminRepository.softDeleteById(id);

        log.info("Admin with id {} deleted", id);
    }

    @Override
    public void changePassword(Long id, @Valid AdminChangePasswordRequest request) {
        Admin admin = adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));

        if (!passwordEncoder.matches(request.currentPassword(), admin.getPassword())) {
            throw new CustomApiException("Current password is incorrect!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        String hashedNewPassword = passwordEncoder.encode(request.newPassword());
        admin.setPassword(hashedNewPassword);
        adminRepository.save(admin);
        log.info("Password changed successfully for admin with id {}", id);
    }

}
