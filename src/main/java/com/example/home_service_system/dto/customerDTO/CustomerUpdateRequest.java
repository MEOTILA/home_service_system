package com.example.home_service_system.dto.customerDTO;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CustomerUpdateRequest(
        @NotNull(message = "Id can not be null for update!")
        Long id,

        @Pattern(regexp = "^[a-zA-Z]+$", message = "Firstname must contain only alphabetic characters!")
        @Size(min = 3, max = 25, message = "First name must be between 3 and 25 characters!")
        String firstName,

        @Pattern(regexp = "^[a-zA-Z]+$", message = "LastName must contain only alphabetic characters!")
        @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters!")
        String lastName,

        @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters!")
        @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters!")
        String username,

        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must contain at least one uppercase letter, " +
                        "one digit, one special character, and be at least 8 characters long!"
        )
        @Size(min = 8, max = 250)
        String password,

        @Pattern(
                regexp = "^[0-9]+$",
                message = "National Code must contain only digits!"
        )
        @Size(min = 10, max = 10, message = "National Id must be 10 digits!")
        String nationalId,

        @Pattern(
                regexp = "^[0-9]+$",
                message = "Phone Number must contain only digits!"
        )
        @Size(min = 11, max = 11, message = "Phone number must be 11 digits!")
        String phoneNumber,

        @Past(message = "Birthday must be a past date!")
        LocalDate birthday,

        @Email(regexp = "^(.+)@(.+)$", message = "Invalid email format!")
        String email,

        List<Long> orderIds,

        List<Long> customerCommentAndRateIds,

        UserStatus userStatus,

        @Min(value = 0, message = "Balance cannot be negative!")
        Long balance) {
}
