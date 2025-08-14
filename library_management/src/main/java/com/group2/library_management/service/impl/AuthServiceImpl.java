package com.group2.library_management.service.impl;

import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.response.UserResponse;
import com.group2.library_management.entity.User;
import com.group2.library_management.entity.enums.RoleType;
import com.group2.library_management.entity.enums.UserStatus;
import com.group2.library_management.exception.EmailAlreadyExistsException;
import com.group2.library_management.mapper.UserMapper;
import com.group2.library_management.repository.UserRepository;
import com.group2.library_management.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor 
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new EmailAlreadyExistsException(request.email());
        });

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(RoleType.CLIENT); 
        user.setStatus(UserStatus.ACTIVE); 

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }
}
