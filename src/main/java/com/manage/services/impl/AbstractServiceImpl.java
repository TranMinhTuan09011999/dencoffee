package com.manage.services.impl;

import com.manage.dto.AbstractNonAuditDTO;
import com.manage.mapper.AbstractMapper;
import com.manage.model.AbstractEntity;
import com.manage.services.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AbstractServiceImpl<R extends JpaRepository, M extends AbstractMapper, D extends AbstractNonAuditDTO, E extends AbstractEntity>
        implements AbstractService<D, E> {
  protected R repository;
  protected M mapper;
  protected D dto;
  protected E entity;
}
