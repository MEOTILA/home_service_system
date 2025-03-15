package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.isDeleted = false")
    Optional<Customer> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT c FROM Customer c WHERE c.isDeleted = false")
    List<Customer> findAllByIsDeletedFalse();

    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.isDeleted = false")
    Optional<Customer> findByUsernameAndIsDeletedFalse(String username);

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByNationalId(String nationalId);

    Optional<Customer> findByEmail(String email);

    @Query("UPDATE Customer c SET c.isDeleted = true WHERE c.id = :id")
    @Modifying
    void softDeleteById(Long id);
}
