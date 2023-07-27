package com.manage.services;

import com.manage.dto.PayrollDTO;
import com.manage.model.Payroll;

import java.util.List;

public interface PayrollService {
  Payroll getPayrollByPayrollId(Long payrollId);
  Payroll save(Payroll payroll);
  List<Payroll> getPayrollByMonthAndYearAndEmployeeId(Integer month, Integer year, Long employeeId);
  List<PayrollDTO> getPayrollByMonthAndYear(Integer month, Integer year);
  List<Payroll> getPayrollByMonthAndYearAndPositionId(Integer month, Integer year, Long positionId);
}