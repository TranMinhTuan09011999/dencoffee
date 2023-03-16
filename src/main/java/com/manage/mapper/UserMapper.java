package com.manage.mapper;

import com.manage.dto.UserDTO;
import com.manage.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<UserDTO, User> {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
