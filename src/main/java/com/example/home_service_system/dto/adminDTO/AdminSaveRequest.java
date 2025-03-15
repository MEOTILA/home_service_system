package com.example.home_service_system.dto.adminDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AdminSaveRequest(
        @NotBlank(message = "Firstname can not be null or empty!")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Firstname must contain only alphabetic characters!")
        @Size(min = 3, max = 25, message = "First name must be between 3 and 25 characters!")
        String firstName,

        @NotBlank(message = "LastName can not be null or empty!")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "LastName must contain only alphabetic characters!")
        @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters!")
        String lastName,

        @NotBlank(message = "Username can not be null or empty!")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters!")
        @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters!")
        String username,

        @NotBlank(message = "Password can not be null or empty!")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must contain at least one uppercase letter, " +
                        "one digit, one special character, and be at least 8 characters long!"
        )
        @Size(min = 8, max = 250)
        String password,

        @NotBlank(message = "National Code can not be null or empty!")
        @Pattern(
                regexp = "^[0-9]+$",
                message = "National Code must contain only digits!"
        )
        @Size(min = 10, max = 10, message = "National ID must be 10 digits!")
        String nationalID,

        @NotBlank(message = "Phone Number can not be null or empty!")
        @Pattern(
                regexp = "^[0-9]+$",
                message = "Phone Number must contain only digits!"
        )
        @Size(min = 11, max = 11, message = "Phone number must be 11 digits!")
        String phoneNumber,

        @NotNull(message = "Birthday cannot be blank or empty!")
        @Past(message = "Birthday must be a past date!")
        LocalDate birthday,

        @NotBlank(message = "Email can not be null or empty!")
        @Email(regexp = "^(.+)@(.+)$", message = "Invalid email format!")
        String email) {
}
