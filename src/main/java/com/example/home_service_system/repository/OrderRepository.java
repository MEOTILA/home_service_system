package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.isDeleted = false")
    Optional<Order> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT o FROM Order o WHERE o.isDeleted = false")
    List<Order> findAllAndIsDeletedFalse();

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.isDeleted = false")
    List<Order> findByCustomerIdAndIsDeletedFalse(Long customerId);

    @Query("SELECT o FROM Order o WHERE o.expert.id = :expertId AND o.isDeleted = false")
    List<Order> findByExpertIdAndIsDeletedFalse(Long expertId);

    @Query("SELECT o FROM Order o WHERE o.subService.id = :subServiceId AND o.isDeleted = false")
    List<Order> findBySubServiceIdAndIsDeletedFalse(Long subServiceId);

    @Query("SELECT o FROM Order o WHERE o.subService.id IN :subServiceIds AND o.isDeleted = false")
    List<Order> findBySubServiceIdInAndIsDeletedFalse(List<Long> subServiceIds);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.isDeleted = false")
    List<Order> findByStatusAndIsDeletedFalse(OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.serviceDate BETWEEN :startDate AND :endDate AND o.isDeleted = false")
    List<Order> findByServiceDateBetweenAndIsDeletedFalse(LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("UPDATE Order o SET o.isDeleted = true WHERE o.id = :id")
    void softDeleteById(Long id);
}
