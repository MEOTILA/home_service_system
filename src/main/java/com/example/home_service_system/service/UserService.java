package com.example.home_service_system.service;

import com.example.home_service_system.dto.userDTO.FilteredUserResponse;
import com.example.home_service_system.dto.userDTO.UserFilterDTO;
import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user) throws MessagingException;

    User update(User user);

    User findUserById(Long id);

    //Long getExpertOrCustomerId(User user);

    List<User> findAllActiveUsers();

    void softDelete(Long id);

    User findByVerificationToken(String verificationToken);

    User findUserByUsername(String username);

    UserResponse findByUsername(String username);

    User findUserByEmail(String email);

    UserResponse findByEmail(String email);

    User findUserByPhoneNumber(String phoneNumber);

    UserResponse findByPhoneNumber(String phoneNumber);

    User findUserByNationalId(String nationalId);

    UserResponse findByNationalId(String nationalId);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    boolean phoneNumberExists(String phoneNumber);

    boolean nationalIdExists(String nationalId);

    String getUserRole(Long userId);

    void changePassword(User user);

    void approveExpert(Long userId);

    void verifyUser(String token);

    FilteredUserResponse findAllWithFilters(UserFilterDTO filter);
}
