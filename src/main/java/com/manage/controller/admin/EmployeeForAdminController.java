package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.AttendanceDTO;
import com.manage.dto.DateRequestDTO;
import com.manage.dto.EmployeeDTO;
import com.manage.jsonview.AttendanceViews;
import com.manage.jsonview.EmployeeViews;
import com.manage.services.bl.GetAttendanceService;
import com.manage.services.bl.GetEmployeeInforService;
import com.manage.services.bl.GetEmployeeNameListService;
import com.manage.services.bl.RegisterEmployeeService;
import com.manage.services.bl.UpdateEmployeeService;
import com.manage.services.bl.UpdateStatusForEmployeeService;
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
@RequestMapping("/api/admin/employee")
public class EmployeeForAdminController {

  @Autowired
  private GetEmployeeInforService getEmployeeInforService;

  @Autowired
  private RegisterEmployeeService registerEmployeeService;

  @Autowired
  private UpdateStatusForEmployeeService updateStatusForEmployeeService;

  @Autowired
  private UpdateEmployeeService updateEmployeeService;

  @Autowired
  private GetAttendanceService getAttendanceService;

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

  @PostMapping("/update-status/{employeeId}/{status}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateStatusForEmployee(@PathVariable(value = "employeeId") Long employeeId,
                                                   @PathVariable(value = "status") Integer status) throws SystemException {
    Boolean result = updateStatusForEmployeeService.updateStatusForEmployee(employeeId, status);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/update-employee")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateStatusForEmployee(@RequestBody EmployeeDTO employeeDTO) throws SystemException {
    Boolean result = updateEmployeeService.updateEmployeeById(employeeDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/get-attendance")
  @PreAuthorize("hasRole('ADMIN')")
  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  public ResponseEntity<?> getAttendanceForToday(@RequestBody DateRequestDTO dateRequestDTO) throws SystemException {
    List<AttendanceDTO> attendanceDTOList = getAttendanceService.getAttendanceForToday(dateRequestDTO.getDate());
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDTOList);
  }
}
