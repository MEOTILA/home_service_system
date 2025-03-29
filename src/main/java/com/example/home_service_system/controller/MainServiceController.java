package com.example.home_service_system.controller;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.service.MainServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main-services")
@RequiredArgsConstructor
@Validated
public class MainServiceController {

    private final MainServiceService mainServiceService;

    @PostMapping
    public ResponseEntity<MainServiceResponse> save(@Valid @RequestBody MainServiceSaveRequest request) {
        return ResponseEntity.ok(mainServiceService.save(request));
    }

    @PutMapping
    public ResponseEntity<MainServiceResponse> update(@Valid @RequestBody MainServiceUpdateRequest request) {
        return ResponseEntity.ok(mainServiceService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteMainServiceAndRelations(@PathVariable Long id) {
        mainServiceService.softDeleteMainServiceAndSubServicesAndOrdersAndSuggestionsAndCommentAndRate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainServiceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mainServiceService.findByIdAndIsDeletedFalse(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MainServiceResponse>> findAll() {
        return ResponseEntity.ok(mainServiceService.findAllAndIsDeletedFalse());
    }
}