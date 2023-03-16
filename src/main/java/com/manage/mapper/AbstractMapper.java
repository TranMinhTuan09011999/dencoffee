package com.manage.mapper;

import com.manage.dto.AbstractNonAuditDTO;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.AbstractEntity;
import org.mapstruct.Context;

public interface AbstractMapper<D extends AbstractNonAuditDTO, E extends AbstractEntity> {

  E toEntity(D dto, @Context CycleAvoidingMappingContext context);

  D toDto(E entity, @Context CycleAvoidingMappingContext context);

}
