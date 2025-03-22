package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateResponse;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateSaveRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateUpdateRequest;
import com.example.home_service_system.entity.CustomerCommentAndRate;

public class CustomerCommentAndRateMapper {
    public static CustomerCommentAndRateResponse to(CustomerCommentAndRate commentAndRate) {
        return new CustomerCommentAndRateResponse(
                commentAndRate.getId(),
                commentAndRate.getOrder() != null ? commentAndRate.getOrder().getId() : null,
                commentAndRate.getRating(),
                commentAndRate.getComment(),
                commentAndRate.getCreatedAt(),
                commentAndRate.getUpdatedAt()
        );
    }

    public static CustomerCommentAndRate fromSaveRequest(CustomerCommentAndRateSaveRequest request) {
        CustomerCommentAndRate commentAndRate = new CustomerCommentAndRate();
        //commentAndRate.setOrder(request.order());
        commentAndRate.setRating(request.rating());
        commentAndRate.setComment(request.comment());
        return commentAndRate;
    }

    public static CustomerCommentAndRate fromUpdateRequest(CustomerCommentAndRateUpdateRequest request) {
        CustomerCommentAndRate commentAndRate = new CustomerCommentAndRate();
        commentAndRate.setId(request.id());
        //commentAndRate.setOrder(request.order());
        commentAndRate.setRating(request.rating());
        commentAndRate.setComment(request.comment());
        return commentAndRate;
    }
}
