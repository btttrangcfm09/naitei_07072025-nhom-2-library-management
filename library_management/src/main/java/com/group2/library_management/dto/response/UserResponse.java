package com.group2.library_management.dto.response;

import com.group2.library_management.entity.enums.RoleType;
import com.group2.library_management.entity.enums.UserStatus;
import java.time.LocalDate;

public record UserResponse(
    Long id,
    String name,
    String email,
    LocalDate dob,
    String phone,
    String address,
    RoleType role,
    UserStatus status
) {}
