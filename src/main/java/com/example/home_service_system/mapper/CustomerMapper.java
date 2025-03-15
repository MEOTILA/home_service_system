package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse to(Customer customer);

    Customer fromSaveRequest(CustomerSaveRequest customerSaveRequest);

    Customer fromUpdateRequest(CustomerUpdateRequest customerUpdateRequest);
}
