package com.manage.mapper;

import com.manage.dto.EmployeeDTO;
import com.manage.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends AbstractMapper<EmployeeDTO, Employee> {
}
