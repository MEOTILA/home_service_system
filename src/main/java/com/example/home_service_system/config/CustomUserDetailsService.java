package com.example.home_service_system.config;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.repository.AdminRepository;
import com.example.home_service_system.repository.CustomerRepository;
import com.example.home_service_system.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check all user types
        Optional<Expert> expert = expertRepository.findByUsername(username);
        if (expert.isPresent()) return new CustomUserDetails(expert.get());

        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) return new CustomUserDetails(customer.get());

        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) return new CustomUserDetails(admin.get());

        throw new UsernameNotFoundException("User not found: " + username);
    }
}