package com.manage.services.impl;

import com.manage.dto.EmployeeDTO;
import com.manage.mapper.EmployeeMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.Employee;
import com.manage.repository.EmployeeRepository;
import com.manage.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private EmployeeMapper employeeMapper;

  @Override
  public List<EmployeeDTO> getAllEmployeeByStatus(Integer status) {
    List<Employee> employeeList = employeeRepository.findAllByStatus(status);
    List<EmployeeDTO> employeeDTOList = employeeList.stream().map(e -> employeeMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return employeeDTOList;
  }

}
