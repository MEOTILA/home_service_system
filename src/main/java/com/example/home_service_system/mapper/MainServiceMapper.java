package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MainServiceMapper {

    MainServiceResponse to(MainService mainService);

    MainService fromSaveRequest(MainServiceSaveRequest request);

    MainService fromUpdateRequest(MainServiceUpdateRequest request);
}
