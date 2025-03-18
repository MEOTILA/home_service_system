package com.example.home_service_system.specification;

import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.enums.UserStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class ExpertSpecification {
    public static Specification<Expert> filterExperts(
            String firstName, String lastName, String username,
            String nationalId, String phoneNumber, String email,
            Integer rating, UserStatus userStatus, Long balance,
            LocalDate createdAt, LocalDate birthday, Long subServiceId) {

        Specification<Expert> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isDeleted"), false);


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

        if (rating != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("rating"), rating));
        }

        if (userStatus != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userStatus"), userStatus));
        }

        if (balance != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("balance"), balance));
        }

        if (createdAt != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("createdAt"), createdAt));
        }

        if (birthday != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("birthday"), birthday));
        }

        if (subServiceId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Object, Object> subServiceJoin = root.join("expertServiceFields");
                return criteriaBuilder.equal(subServiceJoin.get("id"), subServiceId);
            });
        }

        return spec;
    }

}

