package com.example.home_service_system;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.customerDTO.CustomerUpdateRequest;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceSaveRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.mapper.MainServiceMapper;
import com.example.home_service_system.mapper.customMappers.CustomMainServiceMapper;
import com.example.home_service_system.service.*;
import com.example.home_service_system.service.impl.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

@SpringBootApplication(scanBasePackages = "com.example.home_service_system")
public class HomeServiceSystemApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(HomeServiceSystemApplication.class, args);
        var adminService = context.getBean(AdminService.class);
        var customerService = context.getBean(CustomerService.class);
        var expertService = context.getBean(ExpertService.class);
        var mainServiceService = context.getBean(MainServiceService.class);
        var subServiceService = context.getBean(SubServiceService.class);
        var mainServiceMapper = context.getBean(MainServiceMapper.class);
        var orderService = context.getBean(OrderService.class);

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

        AdminSaveRequest adminRequest5 = new AdminSaveRequest(
                "Sam",
                "Serious",
                "Sam",
                "Sam@1234",
                "0012036013",
                "09133201641",
                LocalDate.of(1990, 5, 15),
                "sam@example.com"
        );
        //adminService.save(adminRequest5);
        //System.out.println(adminService.findByUsername("Sam"));
        //System.out.println(adminService.findAll());

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
                "Alan",
                "Parson",
                "Alan",
                "Alan@1234",
                "0011045673",
                "09020132074",
                LocalDate.of(1995, 5, 20),
                "alan@example.com"
        );

        CustomerUpdateRequest customer2 = new CustomerUpdateRequest(
                3L,
                "Alan",
                "Parson",
                "Alan",
                "Alan@1234",
                "0011045673",
                "09020132074",
                LocalDate.of(1995, 5, 20),
                "alans@example.com",
                null,
                null,
                null,
               null
        );

        //customerService.save(customer);
        //customerService.update(customer2);
        //System.out.println(customerService.findAll());

        byte[] dummyImage = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ExpertSaveRequest expert = new ExpertSaveRequest(
                "Tim",
                "Timian",
                "Tim",
                "Tin@1234",
                "0019954327",
                "09184032176",
                LocalDate.of(1990, 8, 15),
                "tim@example.com",
                dummyImage
        );

        //expertService.save(expert);

        //System.out.println(customerService.findAll());
        //System.out.println(expertService.findAll());

        MainServiceSaveRequest mainServiceSaveRequest =
                new MainServiceSaveRequest("Electronic");

        MainServiceUpdateRequest mainServiceUpdateRequest =
                new MainServiceUpdateRequest(2L,"Electronics",null);
       // mainServiceService.update(mainServiceUpdateRequest);
        //mainServiceService.save(mainServiceSaveRequest);
       //System.out.println(mainServiceService.findAll());

       MainServiceResponse mainServiceResponse = mainServiceService.findById(2L);
       MainService mainService = CustomMainServiceMapper.toMainServiceFromResponse(mainServiceResponse);

        SubServiceUpdateRequest subServiceUpdateRequest =
                new SubServiceUpdateRequest(4L,"Repairing Lights"
                ,510000L,"Full Repairing for Lightssss.",mainService,null);
        //subServiceService.save(subServiceSaveRequest);
        //subServiceService.updateSubService(4L,subServiceUpdateRequest);
        //System.out.println(subServiceService.findAllByIsDeletedFalse());

        //subServiceService.addExpertToSubService(2L,2L);
        //subServiceService.addExpertToSubService(1L,1L);
        //subServiceService.addExpertToSubService(3L,1L);
        //subServiceService.addExpertToSubService(4L,1L);

        //subServiceService.removeExpertFromSubService(1L,1L);

        /*SubServiceUpdateRequest subServiceUpdateRequest =
                new SubServiceUpdateRequest(1L,"Kitchen Cleaning",
                        2000000L,"Full cleaning for Kitchen."
                        , mainService,null);*/
        //subServiceService.updateSubService(1L,subServiceUpdateRequest);

        //System.out.println(subServiceService.findAllSubServicesByIsDeletedFalse());
        //mainServiceService.softDelete(1L);
        //subServiceService.softDeleteAllByMainServiceId(1L);

       // System.out.println(expertService.findAll());
        //System.out.println(expertService.findByUsername("David"));


        OrderSaveRequest orderSaveRequest1 = new OrderSaveRequest(
                1L,2L,300L
                ,"i need help to fix my kitchen",
                LocalDateTime.now().plusDays(1L),"Azadi , azadi, azadi azadi"
        );
        orderService.save(orderSaveRequest1);
        System.out.println(orderService.findAll());


    }

}
