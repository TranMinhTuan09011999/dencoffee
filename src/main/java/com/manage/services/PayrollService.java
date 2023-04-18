package com.manage.services;

import com.manage.model.PayRoll;

import java.util.List;

public interface PayrollService {
  List<PayRoll> getPayrollByEmployee(Long employeeId);

}