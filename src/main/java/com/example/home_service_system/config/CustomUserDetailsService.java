package com.example.home_service_system.config;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.repository.AdminRepository;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.repository.ExpertRepository;
import com.example.home_service_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("User not found with username: "
                        + username, CustomApiExceptionType.NOT_FOUND));

        if (user.getUserStatus() != UserStatus.APPROVED) {
            throw new CustomApiException("User is not approved yet.",
                    CustomApiExceptionType.UNAUTHORIZED);
        }

        return new CustomUserDetails(user);
    }
}