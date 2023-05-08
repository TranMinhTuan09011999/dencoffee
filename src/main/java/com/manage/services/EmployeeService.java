package com.manage.services;

import com.manage.dto.EmployeeDTO;

import java.util.Date;
import java.util.List;

public interface EmployeeService {
  List<EmployeeDTO> getAllEmployeeByStatus(Integer status);
  EmployeeDTO getEmployeeByEmployeeId(Long employeeId);
}
