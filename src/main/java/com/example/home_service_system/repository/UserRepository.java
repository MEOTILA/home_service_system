package com.example.home_service_system.repository;

import com.example.home_service_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User>  {

    // Active record queries
    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.id = :id")
    Optional<User> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    List<User> findAllAndIsDeletedFalse();

    // Query methods for active users
    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.username = :username")
    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email = :email")
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.phoneNumber = :phoneNumber")
    Optional<User> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.nationalId = :nationalId")
    Optional<User> findByNationalIdAndIsDeletedFalse(String nationalId);

    // Existence checks (for all users including soft-deleted)
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalId(String nationalId);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    void softDelete(Long id);

    // Role detection
    @Query("SELECT CASE " +
            "WHEN EXISTS (SELECT 1 FROM Admin a WHERE a.user.id = :userId) THEN 'ADMIN' " +
            "WHEN EXISTS (SELECT 1 FROM Expert e WHERE e.user.id = :userId) THEN 'EXPERT' " +
            "WHEN EXISTS (SELECT 1 FROM Customer c WHERE c.user.id = :userId) THEN 'CUSTOMER' " +
            "ELSE 'UNKNOWN' END")
    String findUserRoleByUserId(Long userId);
}