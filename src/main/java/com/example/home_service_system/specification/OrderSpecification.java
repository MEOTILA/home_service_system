package com.example.home_service_system.specification;

import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.entity.enums.PaymentType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class OrderSpecification {
    public static Specification<Order> isNotDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isDeleted"), false);
    }

    public static Specification<Order> hasSubServiceId(Long subServiceId) {
        return (root, query, criteriaBuilder) ->
                subServiceId != null ?
                        criteriaBuilder.equal(root.get("subService").get("id"), subServiceId) :
                        null;
    }

    public static Specification<Order> hasCustomerId(Long customerId) {
        return (root, query, criteriaBuilder) ->
                customerId != null ?
                        criteriaBuilder.equal(root.get("customer").get("id"), customerId) :
                        null;
    }

    public static Specification<Order> hasExpertId(Long expertId) {
        return (root, query, criteriaBuilder) ->
                expertId != null ?
                        criteriaBuilder.equal(root.get("expert").get("id"), expertId) :
                        null;
    }

    public static Specification<Order> hasMinCustomerOfferedCost(Long minCost) {
        return (root, query, criteriaBuilder) ->
                minCost != null ?
                        criteriaBuilder.greaterThanOrEqualTo(root.get("customerOfferedCost"), minCost) :
                        null;
    }

    public static Specification<Order> hasMaxCustomerOfferedCost(Long maxCost) {
        return (root, query, criteriaBuilder) ->
                maxCost != null ?
                        criteriaBuilder.lessThanOrEqualTo(root.get("customerOfferedCost"), maxCost) :
                        null;
    }

    public static Specification<Order> hasCustomerDescription(String description) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(description) ?
                        criteriaBuilder.like(root.get("customerDescription"), "%" + description + "%") :
                        null;
    }

    public static Specification<Order> serviceDateAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                date != null ?
                        criteriaBuilder.greaterThanOrEqualTo(root.get("serviceDate"), date) :
                        null;
    }

    public static Specification<Order> serviceDateBefore(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                date != null ?
                        criteriaBuilder.lessThanOrEqualTo(root.get("serviceDate"), date) :
                        null;
    }

    public static Specification<Order> hasAddress(String address) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(address) ?
                        criteriaBuilder.like(root.get("address"), "%" + address + "%") :
                        null;
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, criteriaBuilder) ->
                status != null ?
                        criteriaBuilder.equal(root.get("status"), status) :
                        null;
    }

    public static Specification<Order> hasPaymentType(PaymentType paymentType) {
        return (root, query, criteriaBuilder) ->
                paymentType != null ?
                        criteriaBuilder.equal(root.get("paymentType"), paymentType) :
                        null;
    }

    public static Specification<Order> createdAtAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                date != null ?
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), date) :
                        null;
    }

    public static Specification<Order> createdAtBefore(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                date != null ?
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), date) :
                        null;
    }

    public static Specification<Order> hasCustomerComment(boolean hasComment) {
        return (root, query, criteriaBuilder) ->
                hasComment ?
                        criteriaBuilder.isNotNull(root.get("customerCommentAndRate")) :
                        criteriaBuilder.isNull(root.get("customerCommentAndRate"));
    }

    public static Specification<Order> serviceDateBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            if (start != null && end != null) {
                return criteriaBuilder.between(root.get("serviceDate"), start, end);
            } else if (start != null) {
                return serviceDateAfter(start).toPredicate(root, query, criteriaBuilder);
            } else if (end != null) {
                return serviceDateBefore(end).toPredicate(root, query, criteriaBuilder);
            }
            return null;
        };
    }

    public static Specification<Order> hasMainServiceId(Long mainServiceId) {
        return (root, query, criteriaBuilder) ->
                mainServiceId != null ?
                        criteriaBuilder.equal(root.get("subService").get("mainService")
                                .get("id"), mainServiceId) :
                        null;
    }
}
