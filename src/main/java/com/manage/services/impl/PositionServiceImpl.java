package com.manage.services.impl;

import com.manage.dto.PositionDTO;
import com.manage.mapper.PositionMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.Position;
import com.manage.repository.PositionRepository;
import com.manage.services.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

  private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private PositionMapper positionMapper;

  @Override
  public List<PositionDTO> getAll() throws SystemException {
    List<PositionDTO> positionDTOList = null;
    try {
      List<Position> positionList = positionRepository.findAll();
      positionDTOList = positionList.stream().map(e -> positionMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
      return positionDTOList;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  @Override
  public Position getPositionById(Long positionId) {
    return positionRepository.getOne(positionId);
  }
}
