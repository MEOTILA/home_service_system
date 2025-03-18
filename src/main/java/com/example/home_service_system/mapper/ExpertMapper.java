package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpertMapper {
    //ExpertResponse to(Expert expert);

    //Expert fromSaveRequest(ExpertSaveRequest expertSaveRequest);

    //Expert fromUpdateRequest(ExpertUpdateRequest expertUpdateRequest);

   // Expert toExpertFromResponse(ExpertResponse expertResponse);

    /*@Mapping(target = "id", source = "id")
    @Mapping(target = "expertImage", source = "expertImage")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "userStatus", source = "userStatus")
    @Mapping(target = "balance", source = "balance")
    ExpertUpdateRequest toUpdateRequest(Expert expert);*/


}
