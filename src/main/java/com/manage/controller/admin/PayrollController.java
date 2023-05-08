package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.EmployeePayrollDTO;
import com.manage.dto.PayrollDTO;
import com.manage.dto.UpdatePayrollInforDTO;
import com.manage.jsonview.PayrollViews;
import com.manage.services.bl.GetAllCurrentPayrollService;
import com.manage.services.bl.GetPayrollForMonthYearService;
import com.manage.services.bl.UpdatePayrollService;
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
@RequestMapping("/api/admin/payroll")
public class PayrollController {

  @Autowired
  private GetPayrollForMonthYearService getPayrollForMonthYearService;

  @Autowired
  private GetAllCurrentPayrollService getAllCurrentPayrollService;

  @Autowired
  private UpdatePayrollService updatePayrollService;

  @GetMapping("/get-payroll/{month}/{year}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getPayrollForMonthYear(@PathVariable(value = "month") Integer month,
                                                  @PathVariable(value = "year") Integer year) throws SystemException {
    List<EmployeePayrollDTO> attendanceDetailsForEmployeeDTOList =
            getPayrollForMonthYearService.getPayrollForMonthYear(month, year);
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDetailsForEmployeeDTOList);
  }

  @GetMapping("/get-all-payroll")
  @JsonView({PayrollViews.PayrollViewForCurrentSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllCurrentPayroll() throws SystemException {
    List<PayrollDTO> result = getAllCurrentPayrollService.getAllCurrentPayroll();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/update-payroll")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updatePayroll(@RequestBody UpdatePayrollInforDTO updatePayrollInforDTO) throws SystemException {
    boolean result = updatePayrollService.updatePayroll(updatePayrollInforDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
