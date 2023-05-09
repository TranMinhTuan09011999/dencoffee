package com.manage.services;

import com.manage.dto.SalaryAdvanceDTO;
import com.manage.model.SalaryAdvance;

import java.util.List;

public interface SalaryAdvanceService {
  List<SalaryAdvanceDTO> getSalaryAdvanceByMonthAndEmployee(Integer month, Integer year, Long employeeId);
  void save(SalaryAdvance salaryAdvance);
}
