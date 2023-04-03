package com.manage.services.impl;

import com.manage.dto.WorkHistoryDTO;
import com.manage.mapper.WorkHistoryMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.WorkHistory;
import com.manage.repository.WorkHistoryRepository;
import com.manage.services.WorkHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkHistoryServiceImpl implements WorkHistoryService {

  @Autowired
  private WorkHistoryRepository workHistoryRepository;

  @Autowired
  private WorkHistoryMapper workHistoryMapper;

  @Override
  public List<WorkHistoryDTO> getWorkHistoryByEmployeeId(Long employeeId) {
    List<WorkHistory> workHistoryList = workHistoryRepository.getWorkHistoryByEmployeeId(employeeId);
    List<WorkHistoryDTO> workHistoryDTOList = workHistoryList.stream().map(e -> workHistoryMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return workHistoryDTOList;
  }

}
