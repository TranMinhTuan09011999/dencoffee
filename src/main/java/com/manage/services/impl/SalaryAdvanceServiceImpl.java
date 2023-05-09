package com.manage.services.impl;

import com.manage.dto.SalaryAdvanceDTO;
import com.manage.mapper.SalaryAdvanceMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.SalaryAdvance;
import com.manage.repository.SalaryAdvanceRepository;
import com.manage.services.SalaryAdvanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryAdvanceServiceImpl implements SalaryAdvanceService {

  @Autowired
  private SalaryAdvanceRepository salaryAdvanceRepository;

  @Autowired
  private SalaryAdvanceMapper salaryAdvanceMapper;

  @Override
  public List<SalaryAdvanceDTO> getSalaryAdvanceByMonthAndEmployee(Integer month, Integer year, Long employeeId) {
    List<SalaryAdvance> salaryAdvanceList = salaryAdvanceRepository.getSalaryAdvanceByMonthAndEmployee(month, year, employeeId);
    List<SalaryAdvanceDTO> salaryAdvanceDTOList = salaryAdvanceList.stream().map(e -> salaryAdvanceMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return salaryAdvanceDTOList;
  }

  @Override
  public void save(SalaryAdvance salaryAdvance) {
    salaryAdvanceRepository.save(salaryAdvance);
  }

}
