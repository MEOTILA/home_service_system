package com.example.home_service_system.controller;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.service.SubServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub-services")
@RequiredArgsConstructor
@Validated
public class SubServiceController {

    private final SubServiceService subServiceService;

    @PostMapping
    public ResponseEntity<SubServiceResponse> save(@Valid @RequestBody SubServiceSaveRequest request) {
        return ResponseEntity.ok(subServiceService.save(request));
    }

    @PutMapping
    public ResponseEntity<SubServiceResponse> update(@Valid @RequestBody SubServiceUpdateRequest request) {
        return ResponseEntity.ok(subServiceService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        subServiceService.
                softDeleteSubServiceAndExpertFieldsAndOrdersAndCommentAndRateAndSuggestionsById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubServiceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subServiceService.findByIdAndIsDeletedFalse(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubServiceResponse>> findAll() {
        return ResponseEntity.ok(subServiceService.findAllAndIsDeletedFalse());
    }

    @GetMapping("/by-main-service/{mainServiceId}")
    public ResponseEntity<List<SubServiceResponse>> findAllByMainServiceId(@PathVariable Long mainServiceId) {
        return ResponseEntity.ok(subServiceService.findAllByMainServiceId(mainServiceId));
    }

    @GetMapping("/by-expert/{expertId}")
    public ResponseEntity<List<SubServiceResponse>> findAllByExpertId(@PathVariable Long expertId) {
        return ResponseEntity.ok(subServiceService.findAllByExpertIdAndIsDeletedFalse(expertId));
    }

    @PostMapping("/{subServiceId}/add-expert/{expertId}")
    public ResponseEntity<Void> addExpertToSubService(@PathVariable Long subServiceId, @PathVariable Long expertId) {
        subServiceService.addExpertToSubService(subServiceId, expertId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{subServiceId}/remove-expert/{expertId}")
    public ResponseEntity<Void> removeExpertFromSubService(@PathVariable Long subServiceId, @PathVariable Long expertId) {
        subServiceService.removeExpertFromSubService(subServiceId, expertId);
        return ResponseEntity.ok().build();
    }
}
