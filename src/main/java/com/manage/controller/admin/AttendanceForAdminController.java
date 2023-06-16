package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.dto.AttendanceForEmployeeRequestDTO;
import com.manage.dto.AttendanceUpdateDTO;
import com.manage.jsonview.AttendanceViews;
import com.manage.services.bl.GetAttendanceForEmployeeService;
import com.manage.services.bl.DownloadExcelForPayrollService;
import com.manage.services.bl.UpdateAttendanceService;
import com.manage.services.bl.UpdatePaymentStatusPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/attendance")
public class AttendanceForAdminController {

  @Autowired
  private GetAttendanceForEmployeeService getAttendanceForEmployeeService;

  @Autowired
  private DownloadExcelForPayrollService downloadExcelForPayrollService;

  @Autowired
  private UpdatePaymentStatusPayrollService updatePaymentStatusPayrollService;

  @Autowired
  private UpdateAttendanceService updateAttendanceService;

  @PostMapping("/get-attendance-for-employee")
  @PreAuthorize("hasRole('ADMIN')")
  @JsonView({AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  public ResponseEntity<?> getAttendanceForEmployee(@RequestBody AttendanceForEmployeeRequestDTO attendanceForEmployeeRequestDTO) throws SystemException {
    List<AttendanceDetailsForEmployeeDTO> attendanceDTOList = getAttendanceForEmployeeService.getAttendanceForEmployee(attendanceForEmployeeRequestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDTOList);
  }

  @PostMapping("/download-excel/{month}/{year}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> downloadExcelForMonthYear(@PathVariable(value = "month") Integer month,
                                        @PathVariable(value = "year") Integer year) throws SystemException {
    Map<String, Object> result = downloadExcelForPayrollService.downloadExcelForPayroll(month, year);
    ByteArrayResource resource = new ByteArrayResource((byte[]) result.get("data"));
    String fileName = String.valueOf(result.get("fileName"));
    final HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
    headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
    return ResponseEntity.ok().contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/octet-stream")).headers(headers)
            .body(resource);
  }

  @PostMapping("/download-excel-all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> downloadExcelAllMonth() throws SystemException {
    Map<String, Object> result = downloadExcelForPayrollService.downloadAllMonthForPayroll();
    ByteArrayResource resource = new ByteArrayResource((byte[]) result.get("data"));
    String fileName = String.valueOf(result.get("fileName"));
    final HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
    headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
    return ResponseEntity.ok().contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/octet-stream")).headers(headers)
            .body(resource);
  }

  @PostMapping("/update-payroll-status/{payrollId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updatePayrollStatus(@PathVariable(value = "payrollId") Long payrollId) throws SystemException {
    boolean result = updatePaymentStatusPayrollService.updatePaymentStatusPayroll(payrollId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/update-attendance")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateAttendance(@RequestBody AttendanceUpdateDTO attendanceUpdateDTO) throws SystemException {
    boolean result = updateAttendanceService.updateAttendance(attendanceUpdateDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
