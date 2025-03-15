package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminResponse to(Admin admin);

    Admin fromSaveRequest(AdminSaveRequest adminSaveRequest);

    Admin fromUpdateRequest(AdminUpdateRequest adminUpdateRequest);
}
