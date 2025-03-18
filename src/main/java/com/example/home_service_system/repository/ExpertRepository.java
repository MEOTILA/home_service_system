package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>
        , JpaSpecificationExecutor<Expert>, PagingAndSortingRepository<Expert, Long> {

    @Query("SELECT e FROM Expert e WHERE e.id = :id AND e.isDeleted = false")
    Optional<Expert> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT e FROM Expert e WHERE e.isDeleted = false")
    List<Expert> findAllByIsDeletedFalse();

    @Query("SELECT e FROM Expert e WHERE e.username = :username AND e.isDeleted = false")
    Optional<Expert> findByUsernameAndIsDeletedFalse(String username);

    @Query("SELECT e FROM Expert e WHERE e.nationalId = :nationalId AND e.isDeleted = false")
    Optional<Expert> findByNationalIdAndIsDeletedFalse(String nationalId);

    @Query("SELECT e FROM Expert e WHERE e.phoneNumber = :phoneNumber AND e.isDeleted = false")
    Optional<Expert> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    @Query("SELECT e FROM Expert e WHERE e.email = :email AND e.isDeleted = false")
    Optional<Expert> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT e FROM Expert e WHERE e.rating = :rating AND e.isDeleted = false")
    List<Expert> findByRatingAndIsDeletedFalse(Integer rating);

    @Query("SELECT e FROM Expert e JOIN e.expertServiceFields esf WHERE esf = :subService AND e.isDeleted = false")
    List<Expert> findByExpertServiceFieldsAndIsDeletedFalse(SubService subService);

    @Query("SELECT e FROM Expert e WHERE e.userStatus = :userStatus AND e.isDeleted = false")
    List<Expert> findByUserStatusAndIsDeletedFalse(UserStatus userStatus);

    @Query("SELECT e FROM Expert e WHERE e.balance = :balance AND e.isDeleted = false")
    List<Expert> findByBalanceAndIsDeletedFalse(Long balance);

    Optional<Expert> findByUsername(String username);

    Optional<Expert> findByPhoneNumber(String phoneNumber);

    Optional<Expert> findByNationalId(String nationalId);

    Optional<Expert> findByEmail(String email);

    @Query("UPDATE Expert e SET e.isDeleted = true WHERE e.id = :id")
    @Modifying
    void softDeleteById(Long id);
}
