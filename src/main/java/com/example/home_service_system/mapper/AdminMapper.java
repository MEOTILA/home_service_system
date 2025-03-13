package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminResponse to(Admin admin);

    Admin from(AdminSaveRequest adminSaveRequest);

    Admin from(AdminUpdateRequest adminUpdateRequest);
}
