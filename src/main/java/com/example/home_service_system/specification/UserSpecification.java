package com.example.home_service_system.specification;

import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserType;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserSpecification {

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(firstName) ?
                        criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%") :
                        null;
    }

    public static Specification<User> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(lastName) ?
                        criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%") :
                        null;
    }

    public static Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(username) ?
                        criteriaBuilder.like(root.get("username"), "%" + username + "%") :
                        null;
    }

    public static Specification<User> hasNationalId(String nationalId) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(nationalId) ?
                        criteriaBuilder.equal(root.get("nationalId"), nationalId) :
                        null;
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(phoneNumber) ?
                        criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber) :
                        null;
    }

    public static Specification<User> hasBirthday(LocalDate birthday) {
        return (root, query, criteriaBuilder) ->
                birthday != null ?
                        criteriaBuilder.equal(root.get("birthday"), birthday) :
                        null;
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(email) ?
                        criteriaBuilder.like(root.get("email"), "%" + email + "%") :
                        null;
    }

    public static Specification<User> hasUserType(UserType userType) {
        return (root, query, criteriaBuilder) ->
                userType != null ?
                        criteriaBuilder.equal(root.get("userType"), userType) :
                        null;
    }

    public static Specification<User> isNotDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isDeleted"), false);
    }

    public static Specification<User> hasExpertRating(Integer minRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating == null) return null;
            query.distinct(true);
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.join("expert").get("rating"),
                    minRating
            );
        };
    }

    public static Specification<User> hasExpertStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(status)) return null;
            query.distinct(true);
            return criteriaBuilder.equal(
                    root.join("expert").get("userStatus").as(String.class),
                    status
            );
        };
    }

    public static Specification<User> hasCustomerStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(status)) return null;
            query.distinct(true);
            return criteriaBuilder.equal(
                    root.join("customer").get("userStatus").as(String.class),
                    status
            );
        };
    }
    public static Specification<User> createdAtBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) {
                return criteriaBuilder.between(root.get("createdAt"), from, to);
            }
            if (from != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<User> hasMinBalance(Long minBalance) {
        return (root, query, criteriaBuilder) -> {
            if (minBalance == null) return null;
            query.distinct(true);
            return criteriaBuilder.or(
                    criteriaBuilder.greaterThanOrEqualTo(
                            root.join("expert", JoinType.LEFT).get("balance"),
                            minBalance
                    ),
                    criteriaBuilder.greaterThanOrEqualTo(
                            root.join("customer", JoinType.LEFT).get("balance"),
                            minBalance
                    )
            );
        };
    }

    public static Specification<User> hasMaxBalance(Long maxBalance) {
        return (root, query, criteriaBuilder) -> {
            if (maxBalance == null) return null;
            query.distinct(true);
            return criteriaBuilder.or(
                    criteriaBuilder.lessThanOrEqualTo(
                            root.join("expert", JoinType.LEFT).get("balance"),
                            maxBalance
                    ),
                    criteriaBuilder.lessThanOrEqualTo(
                            root.join("customer", JoinType.LEFT).get("balance"),
                            maxBalance
                    )
            );
        };
    }
}