package com.manage.mapper;

import com.manage.dto.RoleDTO;
import com.manage.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends AbstractMapper<RoleDTO, Role>{
}
