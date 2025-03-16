package com.example.home_service_system.repository;

import com.example.home_service_system.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    @Query("SELECT s FROM SubService s WHERE s.id = :id AND s.isDeleted = false")
    Optional<SubService> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT s FROM SubService s WHERE s.isDeleted = false")
    List<SubService> findAllByIsDeletedFalse();

    @Query("SELECT s FROM SubService s WHERE s.name = :name AND s.isDeleted = false")
    Optional<SubService> findByNameAndIsDeletedFalse(String name);

    Optional<SubService> findByName (String name);

    @Query("SELECT s FROM SubService s WHERE s.mainService.id = :mainServiceId AND s.isDeleted = false")
    List<SubService> findAllByMainServiceIdAndIsDeletedFalse(Long mainServiceId);

    @Modifying
    @Query("UPDATE SubService s SET s.isDeleted = true WHERE s.id = :id")
    void softDeleteById(Long id);
}
