package com.manage.controller;

import com.manage.dto.EmployeeDTO;
import com.manage.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllEmployee(){
    List<EmployeeDTO> customerDTOList = employeeService.getAllEmployee();
    return ResponseEntity.status(HttpStatus.OK).body(customerDTOList);
  }

}
