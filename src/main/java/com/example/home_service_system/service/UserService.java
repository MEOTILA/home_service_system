package com.example.home_service_system.service;

import com.example.home_service_system.dto.userDTO.FilteredUserResponse;
import com.example.home_service_system.dto.userDTO.UserFilterDTO;
import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    User update(User user);

    User findUserById(Long id);

    List<User> findAllActiveUsers();

    void softDelete(Long id);

    // Specialized queries
    User findUserByUsername(String username);

    UserResponse findByUsername(String username);

    User findUserByEmail(String email);

    UserResponse findByEmail(String email);

    User findUserByPhoneNumber(String phoneNumber);

    UserResponse findByPhoneNumber(String phoneNumber);

    User findUserByNationalId(String nationalId);

    UserResponse findByNationalId(String nationalId);

    // Existence checks
    boolean usernameExists(String username);

    boolean emailExists(String email);

    boolean phoneNumberExists(String phoneNumber);

    boolean nationalIdExists(String nationalId);

    String getUserRole(Long userId);

    void changePassword(User user);

    FilteredUserResponse findAllWithFilters(UserFilterDTO filter);
}
