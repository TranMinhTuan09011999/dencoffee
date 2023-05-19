package com.manage.services;

import com.manage.dto.AttendanceDTO;
import com.manage.model.Attendance;

import java.util.Date;
import java.util.List;

public interface AttendanceService {

  Attendance save(Attendance attendance);

  List<AttendanceDTO> getAttendanceForToday(Date date);

  List<AttendanceDTO> getAttendanceForEmployee(Date dateFrom, Date dateTo);

  List<AttendanceDTO> getAttendanceForPayroll(Long payrollId);

}
