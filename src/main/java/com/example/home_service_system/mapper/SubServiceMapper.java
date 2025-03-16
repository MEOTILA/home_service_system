package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubServiceMapper {

    @Mappings({
            @Mapping(target = "orderListIds", source = "orderList", qualifiedByName = "mapOrderListToIds"),
            @Mapping(target = "expertIds", source = "expertList", qualifiedByName = "mapExpertListToIds")
    })
    SubServiceResponse to(SubService subService);

    SubService fromSaveRequest(SubServiceSaveRequest request);

    SubService fromUpdateRequest(SubServiceUpdateRequest request);
}
