package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.entity.enums.UserType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.AdminMapper;
import com.example.home_service_system.repository.AdminRepository;
import com.example.home_service_system.service.AdminService;
import com.example.home_service_system.service.UserService;
import jakarta.mail.MessagingException;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse save(@Valid AdminSaveRequest request) throws MessagingException {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setNationalId(request.nationalId());
        user.setPhoneNumber(request.phoneNumber());
        user.setBirthday(request.birthday());
        user.setEmail(request.email());
        user.setUserType(UserType.ADMIN);
        user.setUserStatus(UserStatus.NEW);

        //String hashedPassword = passwordEncoder.encode(request.password());
        Admin admin = AdminMapper.fromSaveRequest(request);
        //admin.setPassword(hashedPassword);
        userService.save(user);
        admin.setUser(user);
        adminRepository.save(admin);
        log.info("Admin with id {} saved", admin.getId());
        return AdminMapper.to(admin);
    }

    @Override
    public AdminResponse update(@Valid AdminUpdateRequest request) {
        Admin updatingAdmin = findAdminByIdAndIsDeletedFalse(request.id());
        User updatingUser = userService.findUserById(updatingAdmin.getUser().getId());

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
        userService.update(updatingUser);
        Admin updatedAdmin = adminRepository.save(updatingAdmin);
        log.info("Admin with id {} updated", updatedAdmin.getId());
        return AdminMapper.to(updatedAdmin);
    }

    @Override
    public AdminResponse findByIdAndIsDeletedFalse(Long id) {
        Admin admin = adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return AdminMapper.to(admin);
    }

    @Override
    public Admin findAdminByIdAndIsDeletedFalse(Long id) {
        return adminRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Admin with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<AdminResponse> findAllAndIsDeletedFalse() {
        List<Admin> foundedAdmins = adminRepository.findAllAndIsDeletedFalse();
        return foundedAdmins.stream().map(AdminMapper::to).toList();
    }

    @Override
    public AdminResponse findByUsernameAndIsDeletedFalse(String username) {
        Admin admin = adminRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Admin with username {"
                        + username + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return AdminMapper.to(admin);
    }

    @Override
    public void changePassword(@Valid AdminChangePasswordRequest request) {
        Admin admin = findAdminByIdAndIsDeletedFalse(request.id());
        User updatingUser = userService.findUserById(admin.getUser().getId());
        if (!passwordEncoder.matches(request.currentPassword(), admin.getUser().getPassword())) {
            throw new CustomApiException("Current password is incorrect!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        updatingUser.setPassword(request.newPassword());
        userService.changePassword(updatingUser);
        log.info("Password changed successfully for admin with id {}", request.id());
    }

    @Override
    public void softDeleteById(Long id) {
        Admin admin = findAdminByIdAndIsDeletedFalse(id);
        userService.softDelete(admin.getUser().getId());
        log.info("Admin with id {} deleted", id);
    }
}
