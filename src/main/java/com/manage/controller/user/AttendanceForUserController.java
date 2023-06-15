package com.manage.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.AttendaceSaveRequestDTO;
import com.manage.dto.AttendanceDTO;
import com.manage.dto.AttendanceEndDateTimeUpdateDTO;
import com.manage.dto.DateRequestDTO;
import com.manage.jsonview.AttendanceViews;
import com.manage.services.bl.GetAttendanceService;
import com.manage.services.bl.SaveAttendanceService;
import com.manage.services.bl.UpdateAttendanceService;
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
@RequestMapping("/api/user/attendance")
public class AttendanceForUserController {

    @Autowired
    private SaveAttendanceService saveAttendanceService;

    @Autowired
    private GetAttendanceService getAttendanceService;

    @Autowired
    private UpdateAttendanceService updateAttendanceService;

    @PostMapping("/save-attendance")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ResponseEntity<?> registerEmployee(@RequestBody AttendaceSaveRequestDTO attendaceSaveRequestDTO) throws SystemException {
        Boolean result = saveAttendanceService.saveAttendance(attendaceSaveRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get-attendance")
    @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAttendanceForToday(@RequestBody DateRequestDTO dateRequestDTO) throws SystemException {
        List<AttendanceDTO> attendanceDTOList = getAttendanceService.getAttendanceForToday(dateRequestDTO.getDate());
        return ResponseEntity.status(HttpStatus.OK).body(attendanceDTOList);
    }

    @PostMapping("/update-end-date-time")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateEndDateTime(@RequestBody AttendanceEndDateTimeUpdateDTO attendanceEndDateTimeUpdateDTO) throws SystemException {
        Boolean result = updateAttendanceService.updateEndDateTimeForAttendance(attendanceEndDateTimeUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
