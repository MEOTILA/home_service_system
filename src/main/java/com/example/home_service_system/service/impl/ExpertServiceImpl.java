package com.example.home_service_system.service.impl;

import com.example.home_service_system.dto.expertDTO.ExpertChangePasswordRequest;
import com.example.home_service_system.dto.expertDTO.ExpertResponse;
import com.example.home_service_system.dto.expertDTO.ExpertSaveRequest;
import com.example.home_service_system.dto.expertDTO.ExpertUpdateRequest;
import com.example.home_service_system.entity.Expert;
import com.example.home_service_system.entity.SubService;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.entity.enums.UserType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.ExpertMapper;
import com.example.home_service_system.repository.ExpertRepository;
import com.example.home_service_system.service.ExpertService;
import com.example.home_service_system.service.SubServiceService;
import com.example.home_service_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
@Validated
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    @Lazy
    private SubServiceService subServiceService;

    @Autowired
    public void setSubServiceService(@Lazy SubServiceService subServiceService) {
        this.subServiceService = subServiceService;
    }

    @Override
    public ExpertResponse save(@Valid ExpertSaveRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setNationalId(request.nationalId());
        user.setPhoneNumber(request.phoneNumber());
        user.setBirthday(request.birthday());
        user.setEmail(request.email());
        user.setUserType(UserType.EXPERT);

        Expert expert = ExpertMapper.fromSaveRequest(request);
        expert.setUserStatus(UserStatus.NEW);

        if (request.expertImage().getSize() > 300 * 1024) {
            throw new CustomApiException("Image size must not exceed 300KB!",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        String contentType = request.expertImage().getContentType();
        if (contentType == null || !contentType.equals("image/jpeg")) {
            throw new CustomApiException("Only JPEG images are allowed!",
                    CustomApiExceptionType.BAD_REQUEST);
        }
        try {
            expert.setExpertImage(request.expertImage().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image", e);
        }
        userService.save(user);
        expert.setUser(user);
        expertRepository.save(expert);
        log.info("Expert with id {} saved", expert.getId());
        return ExpertMapper.to(expert);
    }

    @Override
    public ExpertResponse update(@Valid ExpertUpdateRequest request) {
        Expert updatingExpert = findExpertByIdAndIsDeletedFalse(request.id());
        User updatingUser = userService.findUserById(updatingExpert.getUser().getId());

        if (StringUtils.hasText(request.firstName())) {
            updatingUser.setFirstName(request.firstName());
        }
        if (StringUtils.hasText(request.lastName())) {
            updatingUser.setLastName(request.lastName());
        }
        if (StringUtils.hasText(request.username())) {
            userService.usernameExists(request.username());
            updatingUser.setUsername(request.username());
        }
        if (StringUtils.hasText(request.nationalId())) {
            userService.nationalIdExists(request.nationalId());
            updatingUser.setNationalId(request.nationalId());
        }
        if (StringUtils.hasText(request.phoneNumber())) {
            userService.phoneNumberExists(request.phoneNumber());
            updatingUser.setPhoneNumber(request.phoneNumber());
        }
        if (request.birthday() != null) {
            updatingUser.setBirthday(request.birthday());
        }
        if (StringUtils.hasText(request.email())) {
            userService.emailExists(request.email());
            updatingUser.setEmail(request.email());
        }
        if (request.userStatus() != null) {
            updatingExpert.setUserStatus(request.userStatus());
        }
        if (request.balance() != null) {
            updatingExpert.setBalance(request.balance());
        }
        if (request.rating() != null) {
            updatingExpert.setRating(request.rating());
        }
        /*if (request.expertImage() != null &&
                request.expertImage().length > 0) {
            updatingExpert.setExpertImage(request.expertImage());
        }*/
        if (request.expertImage() != null && !request.expertImage().isEmpty()) {
            if (!"image/jpeg".equals(request.expertImage().getContentType())) {
                throw new CustomApiException("Image must be in JPEG format!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            if (request.expertImage().getSize() > 300 * 1024) {
                throw new CustomApiException("Image size must not exceed 300 KB!",
                        CustomApiExceptionType.UNPROCESSABLE_ENTITY);
            }
            try {
                updatingExpert.setExpertImage(request.expertImage().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to process image", e);
            }
        }
        if (request.expertServiceFieldIds() != null && !request.expertServiceFieldIds().isEmpty()) {
            List<SubService> subServices = request.expertServiceFieldIds().stream()
                    .map(subServiceId -> subServiceService
                            .findSubServiceByIdAndIsDeletedFalse(subServiceId))
                    .collect(Collectors.toList());

            updatingExpert.setExpertServiceFields(subServices);
        }
        userService.update(updatingUser);
        Expert updatedExpert = expertRepository.save(updatingExpert);
        log.info("Expert with id {} updated", updatedExpert.getId());
        return ExpertMapper.to(updatedExpert);
    }

    @Override
    public byte[] getExpertImage(Long expertId) {
        Expert expert = findExpertByIdAndIsDeletedFalse(expertId);
        return expert.getExpertImage();
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
    public List<ExpertResponse> findAllAndIsDeletedFalse() {
        List<Expert> foundedExperts = expertRepository.findAllAndIsDeletedFalse();
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
        User updatingUser = userService.findUserById(expert.getUser().getId());
        if (!passwordEncoder.matches(request.currentPassword(), expert.getUser().getPassword())) {
            throw new CustomApiException("Current password is incorrect!",
                    CustomApiExceptionType.UNAUTHORIZED);
        }
        updatingUser.setPassword(request.newPassword());
        userService.changePassword(updatingUser);
        log.info("Password changed successfully for expert with id {}", request.id());
    }

    @Override
    public void softDeleteExpertAndOrdersAndSuggestionsAndCommentAndRatesById(Long id) {
        Expert expert = findExpertByIdAndIsDeletedFalse(id);

        //expert.getOrderList().forEach(order -> order.setDeleted(true));
        expert.getOrderList().forEach(order -> {
            if (order.getCustomerCommentAndRate() != null) {
                order.getCustomerCommentAndRate().setDeleted(true);
            }
            order.setDeleted(true);
        });

        expert.getExpertSuggestionList().forEach(suggestion -> suggestion.setDeleted(true));
        //expert.getExpertServiceFields().clear();

        // expert.setDeleted(true);
        //expertRepository.save(expert);
        userService.softDelete(expert.getUser().getId());
        //expertRepository.softDeleteById(expert.getId());
        log.info("Expert with id {} deleted", id);
    }

    @Override
    public void softDeleteById(Long id) {
        Expert expert = findExpertByIdAndIsDeletedFalse(id);
        userService.softDelete(expert.getUser().getId());
        log.info("Expert with id {} deleted", id);
    }
}
