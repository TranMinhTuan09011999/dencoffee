package com.manage.services.impl;

import com.manage.dto.PayrollDTO;
import com.manage.mapper.PayrollMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.Payroll;
import com.manage.repository.PayrollRepository;
import com.manage.services.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {

  @Autowired
  private PayrollRepository payrollRepository;

  @Autowired
  private PayrollMapper payrollMapper;

  @Override
  public Payroll getPayrollByPayrollId(Long payrollId) {
    return payrollRepository.getOne(payrollId);
  }

  public Payroll save(Payroll payroll) {
    return payrollRepository.save(payroll);
  }

  @Override
  public List<Payroll> getPayrollByMonthAndYearAndEmployeeId(Integer month, Integer year, Long employeeId) {
    List<Payroll> payrollList = payrollRepository.getPayrollByMonthAndYearAndEmployeeId(month, year, employeeId);
    return payrollList;
  }

  @Override
  public List<PayrollDTO> getPayrollByMonthAndYear(Integer month, Integer year) {
    List<Payroll> payrollList = payrollRepository.getPayrollByMonthAndYear(month, year);
    List<PayrollDTO> payrollDTOList = payrollList.stream().map(e -> payrollMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return payrollDTOList;
  }

  @Override
  public List<Payroll> getPayrollByMonthAndYearAndPositionId(Integer month, Integer year, Long positionId) {
    List<Payroll> payrollList = payrollRepository.getPayrollByMonthAndYearAndPositionId(month, year, positionId);
    return payrollList;
  }

}