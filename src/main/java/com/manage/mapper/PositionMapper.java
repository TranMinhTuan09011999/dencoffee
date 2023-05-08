package com.manage.mapper;

import com.manage.dto.EmployeeDTO;
import com.manage.dto.PositionDTO;
import com.manage.model.Employee;
import com.manage.model.Position;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionMapper extends AbstractMapper<PositionDTO, Position> {
}
