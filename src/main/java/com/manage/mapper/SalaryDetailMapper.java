package com.manage.mapper;

import com.manage.dto.SalaryDetailDTO;
import com.manage.model.SalaryDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalaryDetailMapper extends AbstractMapper<SalaryDetailDTO, SalaryDetail> {
}
