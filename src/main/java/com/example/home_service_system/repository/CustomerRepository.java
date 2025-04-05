package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
        , JpaSpecificationExecutor<Customer>, PagingAndSortingRepository<Customer, Long> {


    @Query("SELECT c FROM Customer c WHERE c.user.isDeleted = false")
    List<Customer> findAllAndIsDeletedFalse();

    @Query("SELECT c FROM Customer c WHERE c.user.username = :username AND c.user.isDeleted = false")
    Optional<Customer> findByUsernameAndIsDeletedFalse(String username);

    // Base queries with user join
    @EntityGraph(attributePaths = "user")
    @Query("SELECT c FROM Customer c JOIN c.user u WHERE u.id = :userId AND u.isDeleted = false")
    Optional<Customer> findByUserIdAndIsDeletedFalse(Long userId);

    @EntityGraph(attributePaths = "user")
    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.user.isDeleted = false")
    Optional<Customer> findByIdAndIsDeletedFalse(Long id);

    // Status and balance queries
    @EntityGraph(attributePaths = "user")
    @Query("SELECT c FROM Customer c WHERE c.userStatus = :status AND c.user.isDeleted = false")
    List<Customer> findByStatusAndIsDeletedFalse(UserStatus status);

    @EntityGraph(attributePaths = "user")
    @Query("SELECT c FROM Customer c WHERE c.balance = :balance AND c.user.isDeleted = false")
    List<Customer> findByBalanceAndIsDeletedFalse(Long balance);

    // Profile information queries
    @EntityGraph(attributePaths = "user")
    @Query("SELECT c FROM Customer c WHERE " +
            "(:firstName IS NULL OR c.user.firstName = :firstName) AND " +
            "(:lastName IS NULL OR c.user.lastName = :lastName) AND " +
            "(:birthday IS NULL OR c.user.birthday = :birthday) AND " +
            "c.user.isDeleted = false")
    List<Customer> searchCustomers(String firstName, String lastName, LocalDate birthday);

    // Soft delete operation
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = " +
            "(SELECT c.user.id FROM Customer c WHERE c.id = :customerId)")
    void softDelete(Long customerId);

}
