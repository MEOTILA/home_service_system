package com.example.home_service_system.controller;

import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.service.ExpertService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/experts")
public class ExpertController {

    private final ExpertService expertService;

    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    @GetMapping("/filter")
    public Page<ExpertResponse> filterExperts(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) UserStatus userStatus,
            @RequestParam(required = false) Long balance,
            @RequestParam(required = false) LocalDate createdAt,
            @RequestParam(required = false) LocalDate birthday,
            @RequestParam(required = false) Long subServiceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return expertService.getFilteredExperts(
                firstName, lastName, username, nationalId, phoneNumber,
                email, rating, userStatus, balance, createdAt, birthday, subServiceId,
                page, size);
    }
}
