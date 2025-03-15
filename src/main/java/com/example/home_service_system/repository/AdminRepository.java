package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a WHERE a.id = :id AND a.isDeleted = false")
    Optional<Admin> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT a FROM Admin a WHERE a.isDeleted = false")
    List<Admin> findAllByIsDeletedFalse();

    @Query("SELECT a FROM Admin a WHERE a.username = :username AND a.isDeleted = false")
    Optional<Admin> findByUsernameAndIsDeletedFalse(String username);

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByPhoneNumber(String phoneNumber);

    Optional<Admin> findByNationalId(String nationalId);

    Optional<Admin> findByEmail(String email);

    @Query("UPDATE Admin a SET a.isDeleted = true WHERE a.id = :id")
    @Modifying
    void softDeleteById(Long id);
}
