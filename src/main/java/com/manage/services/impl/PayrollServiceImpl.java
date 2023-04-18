package com.manage.services.impl;

import com.manage.model.PayRoll;
import com.manage.repository.PayrollRepository;
import com.manage.services.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

  @Autowired
  private PayrollRepository payrollRepository;

  @Override
  public List<PayRoll> getPayrollByEmployee(Long employeeId) {
    List<PayRoll> payRollList = payrollRepository.findAllByEmployeeId(employeeId);
    return payRollList;
  }

}