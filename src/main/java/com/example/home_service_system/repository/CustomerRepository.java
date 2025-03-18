package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
, JpaSpecificationExecutor<Customer>, PagingAndSortingRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.isDeleted = false")
    Optional<Customer> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT c FROM Customer c WHERE c.isDeleted = false")
    List<Customer> findAllByIsDeletedFalse();

    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.isDeleted = false")
    Optional<Customer> findByUsernameAndIsDeletedFalse(String username);

    @Query("SELECT c FROM Customer c WHERE c.nationalId = :nationalId AND c.isDeleted = false")
    Optional<Customer> findByNationalIdAndIsDeletedFalse(String nationalId);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber AND c.isDeleted = false")
    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    @Query("SELECT c FROM Customer c WHERE c.email = :email AND c.isDeleted = false")
    Optional<Customer> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT c FROM Customer c WHERE c.firstName = :firstName AND c.isDeleted = false")
    List<Customer> findByFirstNameAndIsDeletedFalse(String firstName);

    @Query("SELECT c FROM Customer c WHERE c.lastName = :lastName AND c.isDeleted = false")
    List<Customer> findByLastNameAndIsDeletedFalse(String lastName);

    @Query("SELECT c FROM Customer c WHERE c.birthday = :birthday AND c.isDeleted = false")
    List<Customer> findByBirthdayAndIsDeletedFalse(LocalDate birthday);

    @Query("SELECT c FROM Customer c WHERE c.createdAt = :createdAt AND c.isDeleted = false")
    List<Customer> findByCreatedAtAndIsDeletedFalse(LocalDate createdAt);

    @Query("SELECT c FROM Customer c WHERE c.userStatus = :userStatus AND c.isDeleted = false")
    List<Customer> findByUserStatusAndIsDeletedFalse(UserStatus userStatus);

    @Query("SELECT c FROM Customer c WHERE c.balance = :balance AND c.isDeleted = false")
    List<Customer> findByBalanceAndIsDeletedFalse(Long balance);

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByNationalId(String nationalId);

    Optional<Customer> findByEmail(String email);

    @Query("UPDATE Customer c SET c.isDeleted = true WHERE c.id = :id")
    @Modifying
    void softDeleteById(Long id);
}
