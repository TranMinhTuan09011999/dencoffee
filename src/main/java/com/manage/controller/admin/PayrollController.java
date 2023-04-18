package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.jsonview.PayrollViews;
import com.manage.services.bl.AddNewSalaryForEmployeeService;
import com.manage.services.bl.GetNewSalaryOfEmployeeService;
import com.manage.services.bl.GetPayrollForMonthYearService;
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
  private AddNewSalaryForEmployeeService addNewSalaryForEmployeeService;

  @Autowired
  private GetNewSalaryOfEmployeeService getNewSalaryOfEmployeeService;

  @GetMapping("/get-payroll/{month}/{year}")
  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getPayrollForMonthYear(@PathVariable(value = "month") Integer month,
                                                  @PathVariable(value = "year") Integer year) throws SystemException {
    List<AttendanceDetailsForEmployeeDTO> attendanceDetailsForEmployeeDTOList =
            getPayrollForMonthYearService.GetPayrollForMonthYear(month, year);
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDetailsForEmployeeDTOList);
  }

  @PostMapping("/new-salary/{employeeId}/{newSalary}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> addNewSalaryForEmployee(@PathVariable(value = "employeeId") Long employeeId,
                                                   @PathVariable(value = "newSalary") Double newSalary) throws SystemException {
    Boolean result = addNewSalaryForEmployeeService.addNewSalaryForEmployee(employeeId, newSalary);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/get-new-salary/{employeeId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getNewSalary(@PathVariable(value = "employeeId") Long employeeId) throws SystemException {
    Double newSalary = getNewSalaryOfEmployeeService.getNewSalaryOfEmployee(employeeId);
    return ResponseEntity.status(HttpStatus.OK).body(newSalary);
  }

}
