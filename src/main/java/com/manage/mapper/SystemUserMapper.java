package com.manage.mapper;

import com.manage.dto.SystemUserDTO;
import com.manage.model.SystemUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemUserMapper extends AbstractMapper<SystemUserDTO, SystemUser> {
}
