package com.example.home_service_system.specification;

import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.enums.UserStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class CustomerSpecification  {
    public static Specification<Customer> filterCustomers(
            String username, String firstName, String lastName,
            String nationalId, String phoneNumber, String email,
            UserStatus userStatus, Long balance,
            LocalDate createdAt, LocalDate birthday) {

        return (root, query, criteriaBuilder) -> {
            Specification<Customer> spec = Specification.where((root1, query1, criteriaBuilder1) ->
                    criteriaBuilder.equal(root1.get("isDeleted"), false));

            if (StringUtils.hasText(username)) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("username"), username));
            }

            if (StringUtils.hasText(firstName)) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("firstName"), firstName));
            }

            if (StringUtils.hasText(lastName)) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("lastName"), lastName));
            }

            if (StringUtils.hasText(nationalId)) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("nationalId"), nationalId));
            }

            if (StringUtils.hasText(phoneNumber)) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("phoneNumber"), phoneNumber));
            }

            if (StringUtils.hasText(email)) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("email"), email));
            }

            if (userStatus != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("userStatus"), userStatus));
            }

            if (balance != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("balance"), balance));
            }

            if (createdAt != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("createdAt"), createdAt));
            }

            if (birthday != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder.equal(root1.get("birthday"), birthday));
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
