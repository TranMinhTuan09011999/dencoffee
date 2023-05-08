package com.manage.services;

import com.manage.dto.SalaryAdvanceDTO;

import java.util.List;

public interface SalaryAdvanceService {
  List<SalaryAdvanceDTO> getSalaryAdvanceByMonthAndEmployee(Integer month, Long employeeId);
}
