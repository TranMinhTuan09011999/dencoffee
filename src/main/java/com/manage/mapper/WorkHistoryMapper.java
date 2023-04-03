package com.manage.mapper;

import com.manage.dto.WorkHistoryDTO;
import com.manage.model.WorkHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkHistoryMapper extends AbstractMapper<WorkHistoryDTO, WorkHistory>{
}
