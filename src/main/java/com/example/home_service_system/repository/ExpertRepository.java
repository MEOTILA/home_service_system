package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>
        , JpaSpecificationExecutor<Expert>, PagingAndSortingRepository<Expert, Long> {

    @Query("SELECT e FROM Expert e WHERE e.user.isDeleted = false")
    List<Expert> findAllAndIsDeletedFalse();

    @Query("SELECT e FROM Expert e WHERE e.user.username = :username AND e.user.isDeleted = false")
    Optional<Expert> findByUsernameAndIsDeletedFalse(String username);

    @EntityGraph(attributePaths = {"user", "expertServiceFields"})
    @Query("SELECT e FROM Expert e JOIN e.user u WHERE u.id = :userId AND u.isDeleted = false")
    Optional<Expert> findByUserIdAndIsDeletedFalse(Long userId);

    @EntityGraph(attributePaths = {"user", "expertServiceFields"})
    @Query("SELECT e FROM Expert e WHERE e.id = :id AND e.user.isDeleted = false")
    Optional<Expert> findByIdAndIsDeletedFalse(Long id);

    // Expert-specific queries
    @EntityGraph(attributePaths = "user")
    @Query("SELECT e FROM Expert e WHERE e.rating = :rating AND e.user.isDeleted = false")
    List<Expert> findByRatingAndIsDeletedFalse(Integer rating);

    @EntityGraph(attributePaths = {"user", "expertServiceFields"})
    @Query("SELECT e FROM Expert e JOIN e.expertServiceFields esf " +
            "WHERE esf = :subService AND e.user.isDeleted = false")
    List<Expert> findBySubServiceAndIsDeletedFalse(SubService subService);

    @EntityGraph(attributePaths = "user")
    @Query("SELECT e FROM Expert e WHERE e.user.userStatus = :status AND e.user.isDeleted = false")
    List<Expert> findByStatusAndIsDeletedFalse(UserStatus status);

    // Search with multiple criteria
    @EntityGraph(attributePaths = {"user", "expertServiceFields"})
    @Query("SELECT e FROM Expert e WHERE " +
            "(:minRating IS NULL OR e.rating >= :minRating) AND " +
            "(:status IS NULL OR e.user.userStatus = :status) AND " +
            "(:minBalance IS NULL OR e.balance >= :minBalance) AND " +
            "e.user.isDeleted = false")
    List<Expert> searchExperts(Integer minRating, UserStatus status, Long minBalance);

    // Soft delete operation
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = " +
            "(SELECT e.user.id FROM Expert e WHERE e.id = :expertId)")
    void softDelete(Long expertId);
}
