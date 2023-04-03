package com.manage.services;

import com.manage.dto.WorkHistoryDTO;

import java.util.List;

public interface WorkHistoryService {
  List<WorkHistoryDTO> getWorkHistoryByEmployeeId(Long employeeId);
}
