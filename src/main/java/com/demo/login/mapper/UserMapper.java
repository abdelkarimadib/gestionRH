package com.demo.login.mapper;

import com.demo.login.dto.UserDTO;
import com.demo.login.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    User mapToEntity(UserDTO userDto);

    @Mapping(ignore = true,target = "password")
    UserDTO mapToDto(User user);

}
