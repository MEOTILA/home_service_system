package com.example.home_service_system.web;

import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/expert-id/{userId}")
    public ResponseEntity<Map<String, Long>> getExpertIdByUserId(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user.getUserType() != UserType.EXPERT || user.getExpert() == null) {
            throw new CustomApiException("Expert profile not found for user with id {"
                    + userId + "}", CustomApiExceptionType.NOT_FOUND);
        }
        return ResponseEntity.ok(Collections.singletonMap("id", user.getExpert().getId()));
    }

    @GetMapping("/customer-id/{userId}")
    public ResponseEntity<Map<String, Long>> getCustomerIdByUserId(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user.getUserType() != UserType.CUSTOMER || user.getCustomer() == null) {
            throw new CustomApiException("Customer profile not found for user with id {"
                    + userId + "}", CustomApiExceptionType.NOT_FOUND);
        }
        return ResponseEntity.ok(Collections.singletonMap("id", user.getCustomer().getId()));
    }
}
