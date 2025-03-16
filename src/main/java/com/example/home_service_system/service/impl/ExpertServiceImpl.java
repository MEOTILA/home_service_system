package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.mapper.customMappers.CustomExpertMapper;
import com.example.home_service_system.repository.ExpertRepository;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.SubServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    //private final ExpertMapper expertMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ExpertResponse save(@Valid ExpertSaveRequest expertSaveRequest) {
        Optional<Expert> optionalExpertByUsername =
                expertRepository.findByUsername(expertSaveRequest.username());
        if (optionalExpertByUsername.isPresent()) {
            throw new CustomApiException("Expert with username {"
                    + expertSaveRequest.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Expert> optionalExpertByPhoneNumber =
                expertRepository.findByPhoneNumber(expertSaveRequest.phoneNumber());
        if (optionalExpertByPhoneNumber.isPresent()) {
            throw new CustomApiException("Expert with phone number {"
                    + expertSaveRequest.phoneNumber() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Expert> optionalExpertByNationalId =
                expertRepository.findByNationalId(expertSaveRequest.nationalId());
        if (optionalExpertByNationalId.isPresent()) {
            throw new CustomApiException("Expert with national ID {"
                    + expertSaveRequest.nationalId() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        Optional<Expert> optionalExpertByEmail =
                expertRepository.findByEmail(expertSaveRequest.email());
        if (optionalExpertByEmail.isPresent()) {
            throw new CustomApiException("Expert with email {"
                    + expertSaveRequest.email() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        String hashedPassword = passwordEncoder.encode(expertSaveRequest.password());
        Expert expert = CustomExpertMapper.fromSaveRequest(expertSaveRequest);
        expert.setPassword(hashedPassword);
        expert.setUserStatus(UserStatus.NEW);
        expertRepository.save(expert);
        log.info("Expert with id {} saved", expert.getId());
        return CustomExpertMapper.to(expert);
    }

    @Override
    public ExpertResponse update(@Valid ExpertUpdateRequest expertUpdateRequest) {
        Expert updatingExpert = expertRepository
                .findByIdAndIsDeletedFalse(expertUpdateRequest.id())
                .orElseThrow(() -> new CustomApiException("Expert with id {"
                        + expertUpdateRequest.id() + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));

        if (StringUtils.hasText(expertUpdateRequest.firstName())) {
            updatingExpert.setFirstName(expertUpdateRequest.firstName());
        }
        if (StringUtils.hasText(expertUpdateRequest.lastName())) {
            updatingExpert.setLastName(expertUpdateRequest.lastName());
        }
        if (StringUtils.hasText(expertUpdateRequest.username())) {
            Optional<Expert> existingExpert = expertRepository
                    .findByUsername(expertUpdateRequest.username());
            if (existingExpert.isPresent() && !existingExpert.get().getId().equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with username {"
                        + expertUpdateRequest.username() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setUsername(expertUpdateRequest.username());
        }
        if (StringUtils.hasText(expertUpdateRequest.password())) {
            String hashedPassword = passwordEncoder.encode(expertUpdateRequest.password());
            updatingExpert.setPassword(hashedPassword);
        }
        if (StringUtils.hasText(expertUpdateRequest.nationalId())) {
            Optional<Expert> existingExpert = expertRepository.findByNationalId(expertUpdateRequest.nationalId());
            if (existingExpert.isPresent() && !existingExpert.get().getId().equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with national ID {"
                        + expertUpdateRequest.nationalId() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setNationalId(expertUpdateRequest.nationalId());
        }
        if (StringUtils.hasText(expertUpdateRequest.phoneNumber())) {
            Optional<Expert> existingExpert = expertRepository.findByPhoneNumber(expertUpdateRequest.phoneNumber());
            if (existingExpert.isPresent() && !existingExpert.get().getId().equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with phone number {"
                        + expertUpdateRequest.phoneNumber() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setPhoneNumber(expertUpdateRequest.phoneNumber());
        }
        if (expertUpdateRequest.birthday() != null) {
            updatingExpert.setBirthday(expertUpdateRequest.birthday());
        }
        if (StringUtils.hasText(expertUpdateRequest.email())) {
            Optional<Expert> existingExpert = expertRepository
                    .findByEmail(expertUpdateRequest.email());
            if (existingExpert.isPresent() && !existingExpert.get().getId().equals(updatingExpert.getId())) {
                throw new CustomApiException("Expert with email {"
                        + expertUpdateRequest.email() + "} already exists!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            updatingExpert.setEmail(expertUpdateRequest.email());
        }
        if (expertUpdateRequest.expertImage() != null && expertUpdateRequest.expertImage().length > 0) {
            updatingExpert.setExpertImage(expertUpdateRequest.expertImage());
        }
        if (expertUpdateRequest.rating() != null) {
            updatingExpert.setRating(expertUpdateRequest.rating());
        }
        if (expertUpdateRequest.userStatus() != null) {
            updatingExpert.setUserStatus(expertUpdateRequest.userStatus());
        }
        if (expertUpdateRequest.balance() != null) {
            updatingExpert.setBalance(expertUpdateRequest.balance());
        }
        /*if (expertUpdateRequest.expertServiceFieldIds() != null) {
            List<SubService> subServices = subServiceService.findAll(expertUpdateRequest.expertServiceFieldIds());
            updatingExpert.setExpertServiceFields(subServices);
        }*/

        Expert updatedExpert = expertRepository.save(updatingExpert);
        log.info("Expert with id {} updated", updatedExpert.getId());
        return CustomExpertMapper.to(updatedExpert);
    }


    @Override
    public ExpertResponse findByIdAndIsDeletedFalse(Long id) {
        Expert expert = expertRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert with id {"
                        + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return CustomExpertMapper.to(expert);
    }

    @Override
    public Expert findExpertByIdAndIsDeletedFalse(Long id) {
        return expertRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert with id {"
                        + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public List<ExpertResponse> findAll() {
        List<Expert> foundedExperts = expertRepository.findAllByIsDeletedFalse();
        return foundedExperts.stream()
                .map(CustomExpertMapper::to)
                .toList();
    }

    @Override
    public ExpertResponse findByUsername(String username) {
        Expert expert = expertRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("Expert with username {"
                        + username + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        return CustomExpertMapper.to(expert);
    }


    @Override
    public void deleteById(Long id) {
        expertRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert with id {" + id + "} not found!",
                        CustomApiExceptionType.NOT_FOUND));
        expertRepository.softDeleteById(id);
        log.info("Expert with id {} deleted", id);
    }

    @Override
    public void changePassword(Long id, @Valid ExpertChangePasswordRequest request) {
        Expert expert = expertRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("Expert with id {"
                        + id + "} not found!", CustomApiExceptionType.NOT_FOUND));

        if (!passwordEncoder.matches(request.currentPassword(), expert.getPassword())) {
            throw new CustomApiException("Current password is incorrect!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        String hashedNewPassword = passwordEncoder.encode(request.newPassword());
        expert.setPassword(hashedNewPassword);
        expertRepository.save(expert);
        log.info("Password changed successfully for expert with id {}", id);
    }
}
