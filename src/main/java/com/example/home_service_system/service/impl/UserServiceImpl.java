package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.UserMapper;
import com.example.home_service_system.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
@Validated
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        usernameExists(user.getUsername());
        phoneNumberExists(user.getPhoneNumber());
        emailExists(user.getEmail());
        nationalIdExists(user.getNationalId());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existingUser = findUserById(user.getId());

        if (StringUtils.hasText(user.getFirstName())) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (StringUtils.hasText(user.getLastName())) {
            existingUser.setLastName(user.getLastName());
        }
        if (StringUtils.hasText(user.getUsername())) {
            // Add username existence check if needed
            // userService.usernameExists(user.getUsername());
            existingUser.setUsername(user.getUsername());
        }
        if (StringUtils.hasText(user.getNationalId())) {
            // Add national ID existence check if needed
            // userService.nationalIdExists(user.getNationalId());
            existingUser.setNationalId(user.getNationalId());
        }
        if (StringUtils.hasText(user.getPhoneNumber())) {
            // Add phone number existence check if needed
            // userService.phoneNumberExists(user.getPhoneNumber());
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getBirthday() != null) {
            existingUser.setBirthday(user.getBirthday());
        }
        if (StringUtils.hasText(user.getEmail())) {
            // Add email existence check if needed
            // userService.emailExists(user.getEmail());
            existingUser.setEmail(user.getEmail());
        }

        return userRepository.save(existingUser);
        /*User existingUser = findUserById(user.getId());

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUsername(user.getUsername());
        existingUser.setNationalId(user.getNationalId());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setBirthday(user.getBirthday());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);*/
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("User with id {"
                        + id + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userRepository.findAllAndIsDeletedFalse();
    }


    @Override
    public void softDelete(Long id) {
        findUserById(id);
        userRepository.softDelete(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("User with username {"
                        + username + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomApiException("User with email {"
                        + email + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new CustomApiException("User with phone number {"
                        + phoneNumber + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByNationalId(String nationalId) {
        return userRepository.findByNationalIdAndIsDeletedFalse(nationalId)
                .orElseThrow(() -> new CustomApiException("User with national ID {"
                        + nationalId + "} not found", CustomApiExceptionType.NOT_FOUND));
    }
    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("User with username {"
                        + username + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomApiException("User with email {"
                        + email + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public UserResponse findByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new CustomApiException("User with phone number {"
                        + phoneNumber + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public UserResponse findByNationalId(String nationalId) {
        User user = userRepository.findByNationalIdAndIsDeletedFalse(nationalId)
                .orElseThrow(() -> new CustomApiException("User with national ID {"
                        + nationalId + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public boolean usernameExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomApiException("User with username {"
                    + username + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomApiException("User with email {"
                    + email + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomApiException("User with phone number {"
                    + phoneNumber + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public boolean nationalIdExists(String nationalId) {
        if (userRepository.existsByUsername(nationalId)) {
            throw new CustomApiException("User with national ID {"
                    + nationalId + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public String getUserRole(Long userId) {
        return userRepository.findUserRoleByUserId(userId);
    }

    @Override
    public void changePassword(User user) {
        User updatingUser = findUserById(user.getId());
        String hashedNewPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedNewPassword);
        userRepository.save(updatingUser);
    }
}
