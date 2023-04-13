package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.AttendanceDTO;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.dto.AttendanceForEmployeeRequestDTO;
import com.manage.dto.DateRequestDTO;
import com.manage.jsonview.AttendanceViews;
import com.manage.services.bl.GetAttendanceForEmployeeService;
import com.manage.services.bl.GetAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/attendance")
public class AttendanceForAdminController {

  @Autowired
  private GetAttendanceService getAttendanceService;

  @Autowired
  private GetAttendanceForEmployeeService getAttendanceForEmployeeService;

  @PostMapping("/get-attendance")
  @PreAuthorize("hasRole('ADMIN')")
  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  public ResponseEntity<?> getAttendanceForToday(@RequestBody DateRequestDTO dateRequestDTO) throws SystemException {
    List<AttendanceDTO> attendanceDTOList = getAttendanceService.getAttendanceForToday(dateRequestDTO.getDate());
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDTOList);
  }

  @PostMapping("/get-attendance-for-employee")
  @PreAuthorize("hasRole('ADMIN')")
  @JsonView({AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  public ResponseEntity<?> getAttendanceForEmployee(@RequestBody AttendanceForEmployeeRequestDTO attendanceForEmployeeRequestDTO) throws SystemException {
    List<AttendanceDetailsForEmployeeDTO> attendanceDTOList = getAttendanceForEmployeeService.getAttendanceForEmployee(attendanceForEmployeeRequestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDTOList);
  }

}
