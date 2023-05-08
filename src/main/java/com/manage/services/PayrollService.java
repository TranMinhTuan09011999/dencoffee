package com.manage.services;

import com.manage.dto.PayrollDTO;
import com.manage.model.Payroll;

import java.util.List;

public interface PayrollService {
  List<PayrollDTO> findAllCurrentPayroll(String currentDay);
  Payroll getPayrollByPayrollId(Long payrollId);
  Payroll save(Payroll payroll);
  List<PayrollDTO> getPayrollByPositionID(Long positionId);
}