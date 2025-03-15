package com.example.home_service_system;

import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
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
		var adminResponse = adminService.save(adminRequest);
		System.out.println("Admin saved: " + adminResponse);

	}

}
