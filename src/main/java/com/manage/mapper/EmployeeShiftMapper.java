package com.manage.mapper;

import com.manage.dto.EmployeeShiftDTO;
import com.manage.model.EmployeeShift;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeShiftMapper extends AbstractMapper<EmployeeShiftDTO, EmployeeShift> {
}
