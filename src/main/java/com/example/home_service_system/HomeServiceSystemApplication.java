package com.example.home_service_system;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.service.AdminService;
import com.example.home_service_system.service.CustomerService;
import com.example.home_service_system.service.ExpertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Base64;

@SpringBootApplication(scanBasePackages = "com.example.home_service_system")
public class HomeServiceSystemApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(HomeServiceSystemApplication.class, args);
        var adminService = context.getBean(AdminService.class);
        var customerService = context.getBean(CustomerService.class);
        var expertService = context.getBean(ExpertService.class);

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

        //System.out.println(adminService.findByUsername("Sattar"));
        //System.out.println(adminService.findAll());

        //todo: Admin change Password
		/*AdminChangePasswordRequest adminChangePasswordRequest =
				new AdminChangePasswordRequest ("Sattar@12345","Sattar@1234");
		adminService.changePassword(1L,adminChangePasswordRequest);*/

        //adminService.deleteById(3L);

        //todo: Admin update
        AdminUpdateRequest adminRequest4 = new AdminUpdateRequest(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                LocalDate.of(1990, 6, 15),
                "sattarrrr@example.com"
        );



        CustomerSaveRequest customer = new CustomerSaveRequest(
                "James",
                "Hetfield",
                "James",
                "James@1234",
                "0012045673",
                "09120132074",
                LocalDate.of(1995, 5, 20),
                "james@example.com"
        );

        //var savedCustomer = customerService.save(customer);
        //System.out.println("Saved Customer: " + savedCustomer);

        byte[] dummyImage = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ExpertSaveRequest expert = new ExpertSaveRequest(
                "David",
                "Gilmour",
                "David",
                "David@1234",
                "0013054327",
                "09124032176",
                LocalDate.of(1990, 8, 15),
                "david@example.com",
                dummyImage
        );

        //var savedExpert = expertService.save(expert);
        //System.out.println("Saved Expert: " + savedExpert);

        System.out.println(customerService.findAll());
        System.out.println(expertService.findAll());

    }

}
