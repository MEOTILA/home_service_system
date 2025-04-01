package com.example.home_service_system.web;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/experts")
@RequiredArgsConstructor
@Validated
public class ExpertController {

    private final ExpertService expertService;

    /*@PostMapping
    public ResponseEntity<ExpertResponse> save(@Valid @RequestBody ExpertSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expertService.save(request));
    }*/
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExpertResponse> save(@Valid @ModelAttribute ExpertSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expertService.save(request));
    }

    @PutMapping
    public ResponseEntity<ExpertResponse> update(@Valid @RequestBody ExpertUpdateRequest request) {
        return ResponseEntity.ok(expertService.update(request));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getExpertImage(@PathVariable Long id) {
        byte[] imageData = expertService.getExpertImage(id);
        if (imageData == null || imageData.length == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ExpertResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expertService.findByIdAndIsDeletedFalse(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpertResponse>> findAll() {
        return ResponseEntity.ok(expertService.findAllAndIsDeletedFalse());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ExpertResponse> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(expertService.findByUsernameAndIsDeletedFalse(username));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ExpertChangePasswordRequest request) {
        expertService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        expertService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/full-delete/{id}")
    public ResponseEntity<Void> softDeleteExpertAndRelations(@PathVariable Long id) {
        expertService.softDeleteExpertAndOrdersAndSuggestionsAndCommentAndRatesById(id);
        return ResponseEntity.noContent().build();
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
