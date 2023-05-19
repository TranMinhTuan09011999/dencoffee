package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.EmployeePayrollDTO;
import com.manage.jsonview.PayrollViews;
import com.manage.services.bl.GetPayrollForMonthYearService;
import com.manage.services.bl.UpdateBonusPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/payroll")
public class PayrollController {

  @Autowired
  private GetPayrollForMonthYearService getPayrollForMonthYearService;

  @Autowired
  private UpdateBonusPayrollService updateBonusPayrollService;

  @GetMapping("/get-payroll/{month}/{year}")
  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getPayrollForMonthYear(@PathVariable(value = "month") Integer month,
                                                  @PathVariable(value = "year") Integer year) throws SystemException {
    List<EmployeePayrollDTO> attendanceDetailsForEmployeeDTOList =
            getPayrollForMonthYearService.getPayrollForMonthYear(month, year);
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDetailsForEmployeeDTOList);
  }

  @PostMapping("/update-bonus/{payrollId}/{bonus}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateBonusPayroll(@PathVariable(value = "payrollId") Long payrollId,
                                       @PathVariable(value = "bonus") Double bonus) throws SystemException {
    boolean result = updateBonusPayrollService.updateBonusPayroll(payrollId, bonus);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
