package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Customer;
import com.example.home_service_system.entity.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpertMapper {

    ExpertResponse to(Expert expert);

    Expert fromSaveRequest(ExpertSaveRequest expertSaveRequest);

    Expert fromUpdateRequest(ExpertUpdateRequest expertUpdateRequest);

    Expert toExpertFromResponse(ExpertResponse expertResponse);

}
