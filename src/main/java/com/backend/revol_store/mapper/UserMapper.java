package com.backend.revol_store.mapper;

import com.backend.revol_store.dto.request.RegisterRequest;
import com.backend.revol_store.dto.response.UserResponse;
import com.backend.revol_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isBanned", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User registerRequestToUser(RegisterRequest request);

    UserResponse userToUserResponse(User user);
}
