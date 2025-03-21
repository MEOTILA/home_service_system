package com.example.home_service_system;

import com.example.home_service_system.dto.adminDTO.AdminChangePasswordRequest;
import com.example.home_service_system.dto.adminDTO.AdminSaveRequest;
import com.example.home_service_system.dto.adminDTO.AdminUpdateRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateSaveRequest;
import com.example.home_service_system.dto.customerCommentAndRateDTO.CustomerCommentAndRateUpdateRequest;
import com.example.home_service_system.dto.customerDTO.CustomerChangePasswordRequest;
import com.example.home_service_system.dto.customerDTO.CustomerResponse;
import com.example.home_service_system.dto.customerDTO.CustomerSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertSuggestionDTO.ExpertSuggestionUpdateRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceResponse;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceSaveRequest;
import com.example.home_service_system.dto.mainServiceDTO.MainServiceUpdateRequest;
import com.example.home_service_system.dto.orderDTO.OrderSaveRequest;
import com.example.home_service_system.dto.orderDTO.OrderUpdateRequest;
import com.example.home_service_system.dto.subServiceDTO.SubServiceUpdateRequest;
import com.example.home_service_system.entity.MainService;
import com.example.home_service_system.entity.Order;
import com.example.home_service_system.entity.enums.OrderStatus;
import com.example.home_service_system.mapper.MainServiceMapper;
import com.example.home_service_system.service.*;
import com.example.home_service_system.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = "com.example.home_service_system")
public class HomeServiceSystemApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(HomeServiceSystemApplication.class, args);
        var adminService = context.getBean(AdminService.class);
        var customerService = context.getBean(CustomerService.class);
        var expertService = context.getBean(ExpertService.class);
        var mainServiceService = context.getBean(MainServiceService.class);
        var subServiceService = context.getBean(SubServiceService.class);
        var orderService = context.getBean(OrderService.class);
        var customerCommentAndRateservice = context.getBean(CustomerCommentAndRateService.class);
        var expertSuggestionService = context.getBean(ExpertSuggestionService.class);


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
        // System.out.println(adminService.findByIdAndIsDeletedFalse(1L));

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
		AdminChangePasswordRequest adminChangePasswordRequest =
				new AdminChangePasswordRequest(1L,
                        "Roger@12345","Roger@1234");
		//adminService.changePassword(adminChangePasswordRequest);

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
        //customerService.save(customer);
        //System.out.println(customerService.findByIdAndIsDeletedFalse(1L));
        CustomerChangePasswordRequest request5 = new CustomerChangePasswordRequest(
                10L,"Alan@123456","Alan@1234");
        //customerService.changePassword(request5);

        CustomerSaveRequest customer33 = new CustomerSaveRequest(
                "Sam",
                "Samer",
                "Sam",
                "Sam@1234",
                "0011035673",
                "09220132074",
                LocalDate.of(1990, 5, 20),
                "sam@example.com"
        );
        // customerService.save(customer33);

/*        CustomerUpdateRequest customer2 = new CustomerUpdateRequest(
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
        );*/

        //customerService.update(customer2);
        //System.out.println(customerService.findAll());
        //customerService.softDeleteById(1L);

        byte[] dummyImage = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ExpertSaveRequest expert = new ExpertSaveRequest(
                "Angel",
                "Angel",
                "Angel",
                "Angel@1234",
                "0019234327",
                "09124232176",
                LocalDate.of(1930, 8, 15),
                "angel@example.com",
                dummyImage
        );
        //expertService.save(expert);
        //System.out.println(expertService.findByIdAndIsDeletedFalse(1L));

        //todo: deleteeeeeeeeeeeeeeeee
        //expertService.softDeleteExpertAndOrdersAndSuggestionsAndCommentAndRatesById(1L);
        //customerService.softDeleteCustomerAndOrdesrAndSuggestionsAndCommentAndRateById(2L);
        mainServiceService
                .softDeleteMainServiceAndSubServicesAndOrdersAndSuggestionsAndCommentAndRate(2L);
        /*subServiceService
                .softDeleteSubServiceAndExpertFieldsAndOrdersAndCommentAndRateAndSuggestionsById(3L);*/
        //orderService.softDeleteOrderAndExpertSuggestionsAndCommentAndRateByOrderId(3L);



        //System.out.println(customerService.findAll());
        //System.out.println(expertService.findAll());
/*
        ExpertChangePasswordRequest request6 = new ExpertChangePasswordRequest(
                6L,"Angel@12345","Angel@12344"
        );*/
        //expertService.changePassword(request6);

        /*MainServiceSaveRequest mainServiceSaveRequest =
                new MainServiceSaveRequest("Electronic");*/

       /* MainServiceUpdateRequest mainServiceUpdateRequest =
                new MainServiceUpdateRequest(1L, "Electronic", null);*/
        //mainServiceService.update(mainServiceUpdateRequest);
        //mainServiceService.save(mainServiceSaveRequest);
        //System.out.println(mainServiceService.findAll());

        //MainServiceResponse mainServiceResponse = mainServiceService.findByIdAndIsDeletedFalse(1L);
        //MainService mainService = CustomMainServiceMapper.toMainServiceFromResponse(mainServiceResponse);

        /*SubServiceSaveRequest subServiceSaveRequest =
                new SubServiceSaveRequest("Fixing laptops"
                        , 910000L, "Full Fixing for laptops.",
                        mainService);*/
        //subServiceService.save(subServiceSaveRequest);
        //subServiceService.updateSubService(1L,subServiceUpdateRequest);
        //System.out.println(subServiceService.findAllByIsDeletedFalse());

        //subServiceService.addExpertToSubService(1L,2L);
        //subServiceService.removeExpertFromSubService(1L,2L);
        // subServiceService.addExpertToSubService(1L,2L);
        //subServiceService.addExpertToSubService(4L,2L);
        //subServiceService.addExpertToSubService(4L,1L);

        //subServiceService.removeExpertFromSubService(1L,1L);
        /*MainServiceResponse mainServiceResponse3 = mainServiceService.findByIdAndIsDeletedFalse(2L);
        MainService mainService3 = MainServiceMapper.toMainServiceFromResponse(mainServiceResponse3);
        SubServiceUpdateRequest subServiceUpdateRequest =
                new SubServiceUpdateRequest(3L, "Kitchen Cleaning",
                        2000000L, "Full cleaning for Kitchens."
                        , mainService3, null);*/
        //subServiceService.update(subServiceUpdateRequest);

        //System.out.println(subServiceService.findAllSubServicesByIsDeletedFalse());
        //mainServiceService.softDelete(1L);
        //subServiceService.softDeleteAllSubServicesByMainServiceId(1L);
        //subServiceService.softDeleteById(1L);


        // System.out.println(expertService.findAll());
        //System.out.println(expertService.findByUsername("David"));

/*
        OrderSaveRequest orderSaveRequest1 = new OrderSaveRequest(
                2L, 1L, 987654L
                , "i need help to fix my apple laptop",
                LocalDateTime.now().plusDays(3L), "New York 33th Street"
        );*/
        //orderService.save(orderSaveRequest1);

        /*OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest(
                1L, 3L, 2L, 2L, 800000L
                , "wqehjiqwejiqwjeqwie",
                LocalDateTime.now().plusDays(1L), "qwjeiqwjeiqwjeqwe",
                OrderStatus.WAITING_FOR_EXPERT_TO_ARRIVE, null
                , null
        );*/
        //orderService.update(orderUpdateRequest);
        //orderService.softDeleteById(3L);
        //System.out.println(orderService.findAllByIsDeletedFalse());
        //System.out.println(expertService.findByIdAndIsDeletedFalse(1L));
        //Order order = orderService.findOrderByIdAndIsDeletedFalse(3L);
        /*CustomerCommentAndRateSaveRequest request = new CustomerCommentAndRateSaveRequest(
                order, 75, "Good job!"
        );*/
        //customerCommentAndRateservice.save(request);
        //customerCommentAndRateservice.softDeleteById(20L);
        //customerCommentAndRateservice.findAllAndIsDeletedFalse();
        // orderService.findByIdAndIsDeletedFalse(2L);

        //Order order1 = orderService.findOrderByIdAndIsDeletedFalse(1L);
        /*CustomerCommentAndRateUpdateRequest request2 = new CustomerCommentAndRateUpdateRequest(
                1L, order1, 39, "Well Doooone!"
        );*/
        //customerCommentAndRateservice.update(request2);

        /*Expert expert1 = expertService.findExpertByIdAndIsDeletedFalse(5L);
        ExpertSuggestionSaveRequest request3 = new ExpertSuggestionSaveRequest(
                order1, expert1, "Meeeeee", 1321L
                , Duration.ofHours(4L), LocalDateTime.now().plusDays(7)
        );*/
       // expertSuggestionService.save(request3);

        /*ExpertSuggestionUpdateRequest request3 = new ExpertSuggestionUpdateRequest(
                3L, order1,"Helloooo",652L,
                Duration.ofDays(2L), LocalDateTime.now().plusDays(7)
        );*/
        //expertSuggestionService.update(request3);
        //expertSuggestionService.softDeleteById(3L);

        //orderService.softDeleteOrderAndExpertSuggestionsAndCommentAndRateByOrderId(1L);
        //System.out.println(expertSuggestionService.findAllByIsDeletedFalse());
        //System.out.println(orderService.findByIdAndIsDeletedFalse(3L));

       /* Order order4 = orderService.findOrderByIdAndIsDeletedFalse(1L);
        ExpertSuggestionUpdateRequest request4 = new ExpertSuggestionUpdateRequest(
                3L,order4,"testttt",133451L, Duration.ofHours(8L)
                ,LocalDateTime.now().plusDays(3)
        );*/
        //expertSuggestionService.update(request4);

        //expertSuggestionService.softDeleteById(3L);

        //System.out.println(expertSuggestionService.findAllByIsDeletedFalse());
        //System.out.println(orderService.findByIdAndIsDeletedFalse(3L));
        //System.out.println(expertService.findByUsername("David"));

        //System.out.println(expertSuggestionService.findAllByExpertIdAndIsDeletedFalse(2L));
        //System.out.println(expertSuggestionService.findAllByOrderIdAndIsDeletedFalse(3L));


        //todo: customer Specification
        Page<CustomerResponse> customers = customerService.getFilteredCustomers(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                5
        );
        //customers.forEach(c -> System.out.println("Found Customers: " + c));

        //todo: expert Specification
        Page<ExpertResponse> experts = expertService.getFilteredExperts(
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                5
        );
        //experts.forEach(e -> System.out.println("Found Experts: " + e));

    }

}
