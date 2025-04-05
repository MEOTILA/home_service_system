package com.example.home_service_system.config;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getUserType().name().toUpperCase())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isDeleted(); // Using your isDeleted flag
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // Or add an 'enabled' field to your User entity if needed
    }

    public UserType getUserType() {
        return user.getUserType();
    }

    public Long getUserId() {
        return user.getId();
    }
}