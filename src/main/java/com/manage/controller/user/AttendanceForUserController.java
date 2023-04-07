package com.manage.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.AttendaceSaveRequestDTO;
import com.manage.dto.AttendanceDTO;
import com.manage.jsonview.AttendanceViews;
import com.manage.services.bl.GetAttendanceService;
import com.manage.services.bl.SaveAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/user/attendance")
public class AttendanceForUserController {

  @Autowired
  private SaveAttendanceService saveAttendanceService;

  @Autowired
  private GetAttendanceService getAttendanceService;

  @PostMapping("/save-attendance")
  public ResponseEntity<?> registerEmployee(@RequestBody AttendaceSaveRequestDTO attendaceSaveRequestDTO) throws SystemException {
    Boolean result = saveAttendanceService.saveAttendance(attendaceSaveRequestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/get-attendance")
  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  public ResponseEntity<?> getAttendanceForToday() throws SystemException {
    List<AttendanceDTO> attendanceDTOList = getAttendanceService.getAttendanceForToday();
    return ResponseEntity.status(HttpStatus.OK).body(attendanceDTOList);
  }

}
