package com.manage.services;

import com.manage.dto.EmployeeDTO;
import com.manage.model.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeService {
  List<EmployeeDTO> getAllEmployeeByStatus(Integer status);
}
