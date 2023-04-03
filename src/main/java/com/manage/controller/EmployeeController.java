package com.manage.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.EmployeeDTO;
import com.manage.jsonview.EmployeeViews;
import com.manage.services.bl.GetEmployeeInforService;
import com.manage.services.bl.RegisterEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  @Autowired
  private GetEmployeeInforService getEmployeeInforService;

  @Autowired
  private RegisterEmployeeService registerEmployeeService;

  @GetMapping("/all-employee/{status}")
  @JsonView({EmployeeViews.EmployeeViewSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllEmployeeByStatus(@PathVariable(value = "status") Integer status) throws SystemException {
    List<EmployeeDTO> customerDTOList = getEmployeeInforService.getEmployeeInforByStatus(status);
    return ResponseEntity.status(HttpStatus.OK).body(customerDTOList);
  }

  @PostMapping("/register-employee")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> registerEmployee(@RequestBody EmployeeDTO employeeDTO) throws SystemException {
    Boolean result = registerEmployeeService.registerEmployee(employeeDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
