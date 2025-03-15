package com.example.home_service_system;

import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.service.AdminService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication(scanBasePackages = "com.example.home_service_system")
public class HomeServiceSystemApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(HomeServiceSystemApplication.class, args);
		var adminService = context.getBean(AdminService.class);

		AdminSaveRequest adminRequest = new AdminSaveRequest(
				"Sattar",
				"Boushehri",
				"Sattar",
				"Sattar@1234",
				"0012035012",
				"09123201670",
				LocalDate.of(1990, 5, 15),
				"sattar@example.com"
		);
		//adminService.save(adminRequest);

		AdminSaveRequest adminRequest2 = new AdminSaveRequest(
				"Roger",
				"Waters",
				"Roger",
				"Roger@12345",
				"0012035013",
				"09123201641",
				LocalDate.of(1990, 5, 15),
				"roger@example.com"
		);
		//adminService.save(adminRequest2);

		AdminUpdateRequest adminRequest3 = new AdminUpdateRequest(
				3L,
				"Sattar",
				"Boushehri",
				"Sattar",
				"Sattar@12345",
				"0012035012",
				"09123201670",
				LocalDate.of(1990, 5, 15),
				"sattar@example.com"
		);
		//adminService.update(adminRequest3);

		//System.out.println(adminService.findByUsername("Sattar"));
		System.out.println(adminService.findAll());
		//adminService.deleteById(3L);

	}

}
