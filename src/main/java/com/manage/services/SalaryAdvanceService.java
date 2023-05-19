package com.manage.services;

import com.manage.dto.SalaryAdvanceDTO;
import com.manage.model.SalaryAdvance;

import java.util.List;

public interface SalaryAdvanceService {
  List<SalaryAdvanceDTO> getSalaryAdvanceByPayroll(Long payrollId);
  void save(SalaryAdvance salaryAdvance);
}
