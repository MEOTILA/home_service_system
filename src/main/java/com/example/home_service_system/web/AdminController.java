package com.example.home_service_system.web;

import com.example.home_service_system.dto.userDTO.FilteredUserResponse;
import com.example.home_service_system.dto.userDTO.UserFilterDTO;
import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/filter")
    public ResponseEntity<FilteredUserResponse> filterUsers(
            @Valid UserFilterDTO filter,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {

        filter.setPage(page);
        filter.setSize(size);
        filter.setSortBy(sortBy);
        filter.setSortDirection(sortDirection);

        return ResponseEntity.ok(userService.findAllWithFilters(filter));
    }

    @PutMapping("/approve-expert/{userId}")
    public ResponseEntity<String> approveExpert(@PathVariable Long userId) {
        try {
            userService.approveExpert(userId);
            return ResponseEntity.ok("Expert approved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Approval failed: " + e.getMessage());
        }
    }
}