package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.SalaryAdvanceDTO;
import com.manage.dto.SalaryAdvanceRequestDTO;
import com.manage.jsonview.SalaryAdvanceViews;
import com.manage.services.bl.AdvanceEmployeeSalaryService;
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
@RequestMapping("/api/admin/salary-advance")
public class SalaryAdvanceController {

  @Autowired
  private AdvanceEmployeeSalaryService advanceEmployeeSalaryService;

  @PostMapping("/save-salary-advance")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> saveSalaryAdvance(@RequestBody SalaryAdvanceRequestDTO salaryAdvanceRequestDTO) throws SystemException {
    Boolean result = advanceEmployeeSalaryService.advanceEmployeeSalary(salaryAdvanceRequestDTO.getPayrollId(),
            salaryAdvanceRequestDTO.getSalaryAdvanceAmount());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/get-salary-advance/{payrollId}")
  @JsonView({SalaryAdvanceViews.GetSalaryAdvanceViewsSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getSalaryAdvance(@PathVariable(value = "payrollId") Long payrollId) throws SystemException {
    List<SalaryAdvanceDTO> result = advanceEmployeeSalaryService.getSalaryAdvanceByPayroll(payrollId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
