package com.example.home_service_system.mapper;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminResponse;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.entity.Admin;
import com.example.home_service_system.entity.User;

public class AdminMapper {
    public static AdminResponse to(Admin admin) {
        if (admin == null || admin.getUser() == null) {
            return null;
        }

        User user = admin.getUser();
        return new AdminResponse(
                admin.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getNationalId(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUserStatus()
        );
    }
    /*public static AdminResponse to(Admin admin) {
        if (admin == null) {
            return null;
        }
        return new AdminResponse(
                admin.getId(),
                admin.getUser().getFirstName(),
                admin.getUser().getLastName(),
                admin.getUser().getUsername(),
                admin.getUser().getNationalId(),
                admin.getUser().getPhoneNumber(),
                admin.getUser().getBirthday(),
                admin.getUser().getEmail(),
                admin.getUser().getCreatedAt(),
                admin.getUser().getUpdatedAt()
        );
    }*/

    public static Admin fromSaveRequest(AdminSaveRequest adminSaveRequest) {
        if (adminSaveRequest == null) {
            return null;
        }
        Admin admin = new Admin();
        /*admin.setFirstName(adminSaveRequest.firstName());
        admin.setLastName(adminSaveRequest.lastName());
        admin.setUsername(adminSaveRequest.username());
        admin.setPassword(adminSaveRequest.password());
        admin.setNationalId(adminSaveRequest.nationalId());
        admin.setPhoneNumber(adminSaveRequest.phoneNumber());
        admin.setBirthday(adminSaveRequest.birthday());
        admin.setEmail(adminSaveRequest.email());*/
        return admin;
    }

    /*public static Admin fromUpdateRequest(AdminUpdateRequest adminUpdateRequest) {
        if (adminUpdateRequest == null) {
            return null;
        }
        Admin admin = new Admin();
        admin.setId(adminUpdateRequest.id());
        admin.setFirstName(adminUpdateRequest.firstName());
        admin.setLastName(adminUpdateRequest.lastName());
        admin.setUsername(adminUpdateRequest.username());
        admin.setPassword(adminUpdateRequest.password());
        admin.setNationalId(adminUpdateRequest.nationalId());
        admin.setPhoneNumber(adminUpdateRequest.phoneNumber());
        admin.setBirthday(adminUpdateRequest.birthday());
        admin.setEmail(adminUpdateRequest.email());
        return admin;
    }*/

    /*public static Admin fromChangePasswordRequest(AdminChangePasswordRequest request) {
        if (request == null) {
            return null;
        }
        Admin admin = new Admin();
        admin.setId(request.id());
        admin.setPassword(request.newPassword());
        return admin;
    }*/
}