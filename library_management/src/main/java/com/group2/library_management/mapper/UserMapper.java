package com.group2.library_management.mapper;

import com.group2.library_management.dto.request.RegisterRequest;
import com.group2.library_management.dto.response.UserResponse;
import com.group2.library_management.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest request);

    UserResponse toUserResponse(User user);
}
