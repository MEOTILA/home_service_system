package com.example.home_service_system.controller;

import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionResponse;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.service.ExpertSuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expert-suggestions")
@RequiredArgsConstructor
@Validated
public class ExpertSuggestionController {

    private final ExpertSuggestionService expertSuggestionService;

    @PostMapping
    public ResponseEntity<ExpertSuggestionResponse> save(@Valid @RequestBody ExpertSuggestionSaveRequest request) {
        return ResponseEntity.ok(expertSuggestionService.save(request));
    }

    @PutMapping
    public ResponseEntity<ExpertSuggestionResponse> update(@Valid @RequestBody ExpertSuggestionUpdateRequest request) {
        return ResponseEntity.ok(expertSuggestionService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        expertSuggestionService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpertSuggestionResponse>> findAll() {
        return ResponseEntity.ok(expertSuggestionService.findAllAndIsDeletedFalse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpertSuggestionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expertSuggestionService.findByIdAndIsDeletedFalse(id));

    }

    @GetMapping("/expert/{expertId}")
    public ResponseEntity<List<ExpertSuggestionResponse>> findAllByExpertId(@PathVariable Long expertId) {
        return ResponseEntity.ok(expertSuggestionService.findAllByExpertIdAndIsDeletedFalse(expertId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ExpertSuggestionResponse>> findAllByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(expertSuggestionService.findAllByOrderIdAndIsDeletedFalse(orderId));
    }
}
