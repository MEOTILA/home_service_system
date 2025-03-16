package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.subServiceDTO.SubServiceResponse;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubServiceMapper {

    @Mappings({
            @Mapping(target = "orderListIds", source = "orderList", qualifiedByName = "mapOrderListToIds"),
            @Mapping(target = "expertIds", source = "expertList", qualifiedByName = "mapExpertListToIds")
    })
    SubServiceResponse to(SubService subService);

    SubService fromSaveRequest(SubServiceSaveRequest request);

    SubService fromUpdateRequest(SubServiceUpdateRequest request);

    @Named("mapOrderListToIds")
    default List<Long> mapOrderListToIds(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        return orders.stream().map(Order::getId).collect(Collectors.toList());
    }

    @Named("mapExpertListToIds")
    default List<Long> mapExpertListToIds(List<Expert> experts) {
        if (experts == null) {
            return null;
        }
        return experts.stream().map(Expert::getId).collect(Collectors.toList());
    }
}
