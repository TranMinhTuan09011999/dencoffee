package com.manage.services;

import com.manage.dto.AttendanceDTO;

import java.util.List;

public interface AttendanceService {

  List<AttendanceDTO> getAttendanceForToday();

}