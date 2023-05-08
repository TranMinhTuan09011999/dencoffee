package com.manage.mapper;

import com.manage.dto.SalaryAdvanceDTO;
import com.manage.model.SalaryAdvance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalaryAdvanceMapper extends AbstractMapper<SalaryAdvanceDTO, SalaryAdvance> {
}
