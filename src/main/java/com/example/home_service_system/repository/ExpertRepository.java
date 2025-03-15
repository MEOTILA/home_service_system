package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    @Query("SELECT e FROM Expert e WHERE e.id = :id AND e.isDeleted = false")
    Optional<Expert> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT e FROM Expert e WHERE e.isDeleted = false")
    List<Expert> findAllByIsDeletedFalse();

    @Query("SELECT e FROM Expert e WHERE e.username = :username AND e.isDeleted = false")
    Optional<Expert> findByUsernameAndIsDeletedFalse(String username);

    Optional<Expert> findByUsername(String username);

    Optional<Expert> findByPhoneNumber(String phoneNumber);

    Optional<Expert> findByNationalId(String nationalId);

    Optional<Expert> findByEmail(String email);

    @Query("UPDATE Expert e SET e.isDeleted = true WHERE e.id = :id")
    @Modifying
    void softDeleteById(Long id);
}
