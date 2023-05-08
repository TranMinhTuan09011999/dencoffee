package com.manage.services;

import com.manage.dto.PositionDTO;
import com.manage.model.Position;

import javax.transaction.SystemException;
import java.util.List;

public interface PositionService {

  List<PositionDTO> getAll() throws SystemException;
  Position getPositionById(Long positionId);

}
