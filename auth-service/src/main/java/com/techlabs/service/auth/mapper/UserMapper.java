package com.techlabs.service.auth.mapper;

import com.techlabs.service.auth.AuthUser;
import com.techlabs.service.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    AuthUser userToAuthUser(User user);
}
