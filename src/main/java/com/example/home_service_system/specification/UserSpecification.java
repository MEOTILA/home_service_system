package com.example.home_service_system.specification;

import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class UserSpecification {
    public static <T extends User> Specification<T> filterUsers(
            String firstName, String lastName, String username,
            String nationalId, String phoneNumber, String email,
            UserStatus userStatus, LocalDate birthday,
            Long balance, Integer rating, Long subServiceId) {

        Specification<T> spec = Specification.where(null);

        if (StringUtils.hasText(firstName)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("firstName"), firstName));
        }

        if (StringUtils.hasText(lastName)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("lastName"), lastName));
        }

        if (StringUtils.hasText(username)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("username"), username));
        }

        if (StringUtils.hasText(nationalId)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("nationalId"), nationalId));
        }

        if (StringUtils.hasText(phoneNumber)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
        }

        if (StringUtils.hasText(email)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("email"), email));
        }

        if (userStatus != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userStatus"), userStatus));
        }

        if (birthday != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("birthday"), birthday));
        }

        if (balance != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                try {
                    return criteriaBuilder.equal(root.get("balance"), balance);
                } catch (IllegalArgumentException e) {
                    return criteriaBuilder.conjunction();
                }
            });
        }

        if (rating != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                try {
                    return criteriaBuilder.equal(root.get("rating"), rating);
                } catch (IllegalArgumentException e) {
                    return criteriaBuilder.conjunction();
                }
            });
        }

        if (subServiceId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                try {
                    Join<Object, Object> subServiceJoin = root.join("expertServiceFields");
                    return criteriaBuilder.equal(subServiceJoin.get("id"), subServiceId);
                } catch (IllegalArgumentException e) {
                    return criteriaBuilder.conjunction();
                }
            });
        }

        return spec;
    }
}