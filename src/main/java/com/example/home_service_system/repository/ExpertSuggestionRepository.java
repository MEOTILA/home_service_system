package com.example.home_service_system.repository;

import com.example.home_service_system.entity.ExpertSuggestion;
import com.example.home_service_system.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertSuggestionRepository extends JpaRepository<ExpertSuggestion, Long> {

    @Query("SELECT e FROM ExpertSuggestion e WHERE e.id = :id AND e.isDeleted = false")
    Optional<ExpertSuggestion> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT e FROM ExpertSuggestion e WHERE e.order.id = :orderId AND e.isDeleted = false")
    List<ExpertSuggestion> findAllByOrderIdAndIsDeletedFalse(Long orderId);

    @Query("SELECT e FROM ExpertSuggestion e WHERE e.isDeleted = false")
    List<ExpertSuggestion> findAllByIsDeletedFalse();

    @Query("UPDATE ExpertSuggestion e SET e.isDeleted = true WHERE e.id = :id")
    @Modifying
    void softDeleteById(Long id);
}
