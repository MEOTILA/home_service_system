package com.example.home_service_system.service.impl;

import com.example.home_service_system.config.EmailService;
import com.example.home_service_system.dto.userDTO.FilteredUserResponse;
import com.example.home_service_system.dto.userDTO.UserFilterDTO;
import com.example.home_service_system.dto.userDTO.UserResponse;
import com.example.home_service_system.entity.User;
import com.example.home_service_system.entity.enums.UserStatus;
import com.example.home_service_system.entity.enums.UserType;
import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import com.example.home_service_system.mapper.UserMapper;
import com.example.home_service_system.repository.UserRepository;
import com.example.home_service_system.service.UserService;
import com.example.home_service_system.specification.UserSpecification;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) throws MessagingException {
        usernameExists(user.getUsername());
        phoneNumberExists(user.getPhoneNumber());
        emailExists(user.getEmail());
        nationalIdExists(user.getNationalId());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        emailService.sendVerificationEmail(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existingUser = findUserById(user.getId());

        if (StringUtils.hasText(user.getFirstName())) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (StringUtils.hasText(user.getLastName())) {
            existingUser.setLastName(user.getLastName());
        }
        if (StringUtils.hasText(user.getUsername())) {
            existingUser.setUsername(user.getUsername());
        }
        if (StringUtils.hasText(user.getNationalId())) {
            existingUser.setNationalId(user.getNationalId());
        }
        if (StringUtils.hasText(user.getPhoneNumber())) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getBirthday() != null) {
            existingUser.setBirthday(user.getBirthday());
        }
        if (StringUtils.hasText(user.getEmail())) {
            existingUser.setEmail(user.getEmail());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomApiException("User with id {"
                        + id + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    /*@Override
    public Long getExpertOrCustomerId(User user) {
        if (user.getUserType() == UserType.EXPERT) {
            if (user.getExpert() == null) {
                throw new CustomApiException("Expert profile not found for user with id {"
                        + user.getId() + "}", CustomApiExceptionType.NOT_FOUND);
            }
            return user.getExpert().getId();
        } else if (user.getUserType() == UserType.CUSTOMER) {
            if (user.getCustomer() == null) {
                throw new CustomApiException("Customer profile not found for user with id {"
                        + user.getId() + "}", CustomApiExceptionType.NOT_FOUND);
            }
            return user.getCustomer().getId();
        } else {
            throw new CustomApiException("User with id {"
                    + user.getId() + "} is not an expert or customer",
                    CustomApiExceptionType.BAD_REQUEST);
        }
    }*/


    @Override
    public List<User> findAllActiveUsers() {
        return userRepository.findAllAndIsDeletedFalse();
    }


    @Override
    public void softDelete(Long id) {
        findUserById(id);
        userRepository.softDelete(id);
    }

    @Override
    public User findByVerificationToken(String verificationToken) {
        return userRepository.findByVerificationToken(verificationToken)
                .orElseThrow(() -> new CustomApiException("User with verification token {"
                        + verificationToken
                        + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("User with username {"
                        + username + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomApiException("User with email {"
                        + email + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new CustomApiException("User with phone number {"
                        + phoneNumber + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public User findUserByNationalId(String nationalId) {
        return userRepository.findByNationalIdAndIsDeletedFalse(nationalId)
                .orElseThrow(() -> new CustomApiException("User with national ID {"
                        + nationalId + "} not found", CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomApiException("User with username {"
                        + username + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomApiException("User with email {"
                        + email + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public UserResponse findByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new CustomApiException("User with phone number {"
                        + phoneNumber + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public UserResponse findByNationalId(String nationalId) {
        User user = userRepository.findByNationalIdAndIsDeletedFalse(nationalId)
                .orElseThrow(() -> new CustomApiException("User with national ID {"
                        + nationalId + "} not found", CustomApiExceptionType.NOT_FOUND));
        return UserMapper.to(user);
    }

    @Override
    public boolean usernameExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomApiException("User with username {"
                    + username + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomApiException("User with email {"
                    + email + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomApiException("User with phone number {"
                    + phoneNumber + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public boolean nationalIdExists(String nationalId) {
        if (userRepository.existsByNationalId(nationalId)) {
            throw new CustomApiException("User with national ID {"
                    + nationalId + "} already exists!",
                    CustomApiExceptionType.UNPROCESSABLE_ENTITY);
        }
        return false;
    }

    @Override
    public String getUserRole(Long userId) {
        return userRepository.findUserRoleByUserId(userId);
    }

    @Override
    public void changePassword(User user) {
        User updatingUser = findUserById(user.getId());
        String hashedNewPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedNewPassword);
        userRepository.save(updatingUser);
    }

    @Override
    public void approveExpert(Long userId) {
        User user = findUserById(userId);
        if (user.getUserType() == UserType.EXPERT && user.getUserStatus() == UserStatus.PENDING) {
            user.setUserStatus(UserStatus.APPROVED);
            userRepository.save(user);
        } else {
            throw new CustomApiException("User is not an expert or is not in PENDING status.",
                    CustomApiExceptionType.BAD_REQUEST);
        }
    }

    @Override
    public void verifyUser(String token) {
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);

        if (optionalUser.isEmpty()) {
            throw new CustomApiException("Email is already verified or invalid status.",
                    CustomApiExceptionType.BAD_REQUEST);
        }

        User user = optionalUser.get();
        if (user.getUserStatus() == UserStatus.NEW) {
            switch (user.getUserType()) {
                case ADMIN:
                case CUSTOMER:
                    user.setUserStatus(UserStatus.APPROVED);
                    break;
                case EXPERT:
                    user.setUserStatus(UserStatus.PENDING);
                    break;
                default:
                    throw new CustomApiException("Invalid user role.",
                            CustomApiExceptionType.BAD_REQUEST);
            }
            user.setVerificationToken(null);
            userRepository.save(user);
        } else {
            throw new CustomApiException("Email is already verified or invalid status."
                    ,CustomApiExceptionType.BAD_REQUEST);
        }
    }

    @Override
    public FilteredUserResponse findAllWithFilters(UserFilterDTO filter) {
        Specification<User> spec = buildSpecification(filter);

        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.fromString(filter.getSortDirection()),
                        filter.getSortBy()
                ));

        Page<User> userPage = userRepository.findAll(spec, pageable);
        return UserMapper.toFilteredResponse(
                userPage,
                filter.getSortBy(),
                filter.getSortDirection(),
                filter.getCreatedAtFrom(),
                filter.getCreatedAtTo(),
                filter.getMinBalance(),
                filter.getMaxBalance(),
                filter.getUserType(),
                filter.getExpertStatus(),
                filter.getCustomerStatus()
        );
    }

    private Specification<User> buildSpecification(UserFilterDTO filter) {
        Specification<User> spec = Specification.where(UserSpecification.isNotDeleted());

        if (filter != null) {
            if (StringUtils.hasText(filter.getFirstName())) {
                spec = spec.and(UserSpecification.hasFirstName(filter.getFirstName()));
            }
            if (StringUtils.hasText(filter.getLastName())) {
                spec = spec.and(UserSpecification.hasLastName(filter.getLastName()));
            }
            if (StringUtils.hasText(filter.getUsername())) {
                spec = spec.and(UserSpecification.hasUsername(filter.getUsername()));
            }
            if (StringUtils.hasText(filter.getNationalId())) {
                spec = spec.and(UserSpecification.hasNationalId(filter.getNationalId()));
            }
            if (StringUtils.hasText(filter.getPhoneNumber())) {
                spec = spec.and(UserSpecification.hasPhoneNumber(filter.getPhoneNumber()));
            }
            if (filter.getBirthday() != null) {
                spec = spec.and(UserSpecification.hasBirthday(filter.getBirthday()));
            }
            if (StringUtils.hasText(filter.getEmail())) {
                spec = spec.and(UserSpecification.hasEmail(filter.getEmail()));
            }
            if (filter.getUserType() != null) {
                spec = spec.and(UserSpecification.hasUserType(filter.getUserType()));
            }
            if (filter.getMinExpertRating() != null) {
                spec = spec.and(UserSpecification.hasExpertRating(filter.getMinExpertRating()));
            }
            if (StringUtils.hasText(filter.getExpertStatus())) {
                spec = spec.and(UserSpecification.hasExpertStatus(filter.getExpertStatus()));
            }
            if (StringUtils.hasText(filter.getCustomerStatus())) {
                spec = spec.and(UserSpecification.hasCustomerStatus(filter.getCustomerStatus()));
            }
            if (filter.getCreatedAtFrom() != null || filter.getCreatedAtTo() != null) {
                spec = spec.and(UserSpecification.createdAtBetween(
                        filter.getCreatedAtFrom(),
                        filter.getCreatedAtTo()
                ));
            }

            if (filter.getMinBalance() != null) {
                spec = spec.and(UserSpecification.hasMinBalance(filter.getMinBalance()));
            }

            if (filter.getMaxBalance() != null) {
                spec = spec.and(UserSpecification.hasMaxBalance(filter.getMaxBalance()));
            }
            if (filter.getMinOrderCount() != null) {
                spec = spec.and(UserSpecification.hasMinOrderCount(filter.getMinOrderCount()));
            }

            if (filter.getMaxOrderCount() != null) {
                spec = spec.and(UserSpecification.hasMaxOrderCount(filter.getMaxOrderCount()));
            }
        }

        return spec;
    }
}
