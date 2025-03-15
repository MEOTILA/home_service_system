package com.example.home_service_system.repository;

import com.example.home_service_system.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsernameAndPhoneNumberAndNationalIDAndEmail(String username,
                                                                      String phoneNumber,
                                                                      String nationalID,
                                                                      String email);
}
