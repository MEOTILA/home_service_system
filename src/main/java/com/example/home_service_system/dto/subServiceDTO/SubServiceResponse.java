package com.example.home_service_system.dto.subServiceDTO;

import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.entity.Order;

import java.util.List;

public record SubServiceResponse (
        Long id,
        String name,
        Long baseCost,
        String description,
        MainService mainService,
        List<Long> orderListIds,
        List<Long> expertIds
){
}
