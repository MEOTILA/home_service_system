package com.example.home_service_system.dto.expertDTO;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record ExpertSaveRequest(
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
        @Size(min = 10, max = 10, message = "National Id must be 10 digits!")
        String nationalId,

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
        String email,

        /*@NotNull(message = "Expert image cannot be null!")
        @Size(max = 307200, message = "Image size must not exceed 300KB!")
        byte[] expertImage*/

        @NotNull(message = "Expert image cannot be null!")
        MultipartFile expertImage
) {
}
