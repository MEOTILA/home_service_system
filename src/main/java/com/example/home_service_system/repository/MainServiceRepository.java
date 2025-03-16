package com.example.home_service_system.repository;

import com.example.home_service_system.entity.MainService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MainServiceRepository extends JpaRepository<MainService, Long> {

    @Query("SELECT m FROM MainService m WHERE m.id = :id AND m.isDeleted = false")
    Optional<MainService> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT m FROM MainService m WHERE m.isDeleted = false")
    List<MainService> findAllByIsDeletedFalse();

    @Query("SELECT m FROM MainService m WHERE m.name = :name AND m.isDeleted = false")
    Optional<MainService> findByNameAndIsDeletedFalse(String name);

    Optional<MainService> findByName(String name);

    @Modifying
    @Query("UPDATE MainService m SET m.isDeleted = true WHERE m.id = :id")
    void softDeleteById(Long id);
}
