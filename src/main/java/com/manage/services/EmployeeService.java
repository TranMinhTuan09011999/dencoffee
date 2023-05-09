package com.manage.services;

import com.manage.dto.EmployeeDTO;
import com.manage.model.Employee;
import java.util.List;

public interface EmployeeService {
  List<EmployeeDTO> getAllEmployeeByStatus(Integer status);
  EmployeeDTO getEmployeeByEmployeeId(Long employeeId);
  Employee getEmployeeById(Long employeeId);
}
