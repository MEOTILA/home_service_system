package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.repository.ExpertRepository;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.specification.ExpertSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ExpertResponse save(@Valid ExpertSaveRequest request) {
        Optional<Expert> optionalExpertByUsername =
                expertRepository.findByUsername(request.username());
        if (optionalExpertByUsername.isPresent()) {
            throw new CustomApiException("Expert with username {"
                    + request.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Expert> optionalExpertByPhoneNumber =
                expertRepository.findByPhoneNumber(request.phoneNumber());
        if (optionalExpertByPhoneNumber.isPresent()) {
            throw new CustomApiException("Expert with phone number {"
                    + request.phoneNumber() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Expert> optionalExpertByNationalId =
                expertRepository.findByNationalId(request.nationalId());
        if (optionalExpertByNationalId.isPresent()) {
            throw new CustomApiException("Expert with national ID {"
                    + request.nationalId() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Expert> optionalExpertByEmail =
                expertRepository.findByEmail(request.email());
        if (optionalExpertByEmail.isPresent()) {
            throw new CustomApiException("Expert with email {"
                    + request.email() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        String hashedPassword = passwordEncoder.encode(request.password());
        Expert expert = ExpertMapper.fromSaveRequest(request);
        expert.setPassword(hashedPassword);
        expert.setUserStatus(UserStatus.NEW);
        expertRepository.save(expert);
        log.info("Expert with id {} saved", expert.getId());
        return ExpertMapper.to(expert);
    }

    @Override
    public ExpertResponse update(@Valid ExpertUpdateRequest request) {
        Expert updatingExpert = findExpertByIdAndIsDeletedFalse(request.id());

        if (StringUtils.hasText(request.firstName())) {
            updatingExpert.setFirstName(request.firstName());
        }
        if (StringUtils.hasText(request.lastName())) {
            updatingExpert.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.username())) {
            Optional<Expert> existingExpert = expertRepository
                    .findByUsername(request.username());
            if (existingExpert.isPresent() && !existingExpert.get().getId()
                    .equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with username {"
                        + request.username() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setUsername(request.username());
        }
        if (StringUtils.hasText(request.password())) {
            String hashedPassword = passwordEncoder.encode(request.password());
            updatingExpert.setPassword(hashedPassword);
        }
        if (StringUtils.hasText(request.nationalId())) {
            Optional<Expert> existingExpert = expertRepository
                    .findByNationalId(request.nationalId());
            if (existingExpert.isPresent() && !existingExpert.get().getId().
                    equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with national ID {"
                        + request.nationalId() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setNationalId(request.nationalId());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            Optional<Expert> existingExpert = expertRepository
                    .findByPhoneNumber(request.phoneNumber());
            if (existingExpert.isPresent() && !existingExpert.get().getId()
                    .equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with phone number {"
                        + request.phoneNumber() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setPhoneNumber(request.phoneNumber());
        }
        if (request.birthday() != null) {
            updatingExpert.setBirthday(request.birthday());
        }
        if (StringUtils.hasText(request.email())) {
            Optional<Expert> existingExpert = expertRepository
                    .findByEmail(request.email());
            if (existingExpert.isPresent() && !existingExpert.get().getId()
                    .equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with email {"
                        + request.email() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setEmail(request.email());
        }
        if (request.expertImage() != null &&
                request.expertImage().length > 0) {
            updatingExpert.setExpertImage(request.expertImage());
        }
        if (request.rating() != null) {
            updatingExpert.setRating(request.rating());
        }
        if (request.userStatus() != null) {
            updatingExpert.setUserStatus(request.userStatus());
        }
        if (request.balance() != null) {
            updatingExpert.setBalance(request.balance());
        }

        Expert updatedExpert = expertRepository.save(updatingExpert);
        log.info("Expert with id {} updated", updatedExpert.getId());
        return ExpertMapper.to(updatedExpert);
    }


    @Override
    public ExpertResponse findByIdAndIsDeletedFalse(Long id) {
        Expert expert = expertRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return ExpertMapper.to(expert);
    }

    @Override
    public Expert findExpertByIdAndIsDeletedFalse(Long id) {
        return expertRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<ExpertResponse> findAllByIsDeletedFalse() {
        List<Expert> foundedExperts = expertRepository.findAllByIsDeletedFalse();
        return foundedExperts.stream()
                .map(ExpertMapper::to)
                .toList();
    }

    @Override
    public ExpertResponse findByUsernameAndIsDeletedFalse(String username) {
        Expert expert = expertRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Expert with username {"
                        + username + "} not found!", CustomApiExceptionType.NOT_FOUND));
        return ExpertMapper.to(expert);
    }


    @Override
    public void changePassword(@Valid ExpertChangePasswordRequest request) {
        Expert expert = findExpertByIdAndIsDeletedFalse(request.id());

        if (!passwordEncoder.matches(request.currentPassword(), expert.getPassword())) {
            throw new CustomApiException("Current password is incorrect!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        String hashedNewPassword = passwordEncoder.encode(request.newPassword());
        expert.setPassword(hashedNewPassword);
        expertRepository.save(expert);
        log.info("Password changed successfully for expert with id {}", request.id());
    }

    @Override
    public void softDeleteById(Long id) {
        findExpertByIdAndIsDeletedFalse(id);
        expertRepository.softDeleteById(id);
        log.info("Expert with id {} deleted", id);
    }

    @Override
    public Page<ExpertResponse> getFilteredExperts(
            String firstName, String lastName, String username,
            String nationalId, String phoneNumber, String email,
            Integer rating, UserStatus userStatus, Long balance,
            LocalDate createdAt, LocalDate birthday,
            Long subServiceId, int page, int size) {

        Specification<Expert> spec = ExpertSpecification.filterExperts(
                firstName, lastName, username, nationalId, phoneNumber,
                email, rating, userStatus, balance, createdAt,
                birthday, subServiceId);

        Pageable pageable = PageRequest.of(page, size);
        Page<Expert> customerPage = expertRepository.findAll(spec, pageable);

        return customerPage.map(ExpertMapper::to);
    }
}
