package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @EntityGraph(attributePaths = "user")
    @Query("SELECT a FROM Admin a JOIN a.user u WHERE u.id = :userId AND u.isDeleted = false")
    Optional<Admin> findByUserIdAndIsDeletedFalse(Long userId);

    @EntityGraph(attributePaths = "user")
    @Query("SELECT a FROM Admin a WHERE a.id = :id AND a.user.isDeleted = false")
    Optional<Admin> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT a FROM Admin a WHERE a.user.isDeleted = false")
    List<Admin> findAllAndIsDeletedFalse();

    @Query("SELECT a FROM Admin a WHERE a.user.username = :username AND a.user.isDeleted = false")
    Optional<Admin> findByUsernameAndIsDeletedFalse(String username);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = " +
            "(SELECT a.user.id FROM Admin a WHERE a.id = :adminId)")
    void softDeleteById(Long adminId);
}

