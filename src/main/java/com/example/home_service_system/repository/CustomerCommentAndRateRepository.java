package com.example.home_service_system.repository;

import com.example.home_service_system.entity.CustomerCommentAndRate;
import com.example.home_service_system.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerCommentAndRateRepository
        extends JpaRepository<CustomerCommentAndRate, Long> {

    @Query("SELECT c FROM CustomerCommentAndRate c WHERE c.id = :id AND c.isDeleted = false")
    Optional<CustomerCommentAndRate> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT c FROM CustomerCommentAndRate c WHERE c.isDeleted = false")
    List<CustomerCommentAndRate> findAllByIsDeletedFalse();

    @Query("SELECT c FROM CustomerCommentAndRate c WHERE c.order = :order AND c.isDeleted = false")
    List<CustomerCommentAndRate> findByOrderAndIsDeletedFalse(Order order);

    @Modifying
    @Query("UPDATE CustomerCommentAndRate c SET c.isDeleted = true WHERE c.id = :id")
    void softDeleteById(Long id);
}
