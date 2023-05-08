package com.manage.mapper;

import com.manage.dto.PayrollDTO;
import com.manage.model.Payroll;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollMapper extends AbstractMapper<PayrollDTO, Payroll>{
}
