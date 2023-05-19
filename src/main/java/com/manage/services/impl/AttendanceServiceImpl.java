package com.manage.services.impl;

import com.manage.dto.AttendanceDTO;
import com.manage.mapper.AttendanceMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.Attendance;
import com.manage.repository.AttendanceRepository;
import com.manage.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Autowired
  private AttendanceMapper attendanceMapper;

  @Override
  public Attendance save(Attendance attendance) {
    return attendanceRepository.save(attendance);
  }

  @Override
  public List<AttendanceDTO> getAttendanceForToday(Date date) {
    List<Attendance> attendanceList = attendanceRepository.getAttendanceByToday(date);
    List<AttendanceDTO> attendanceDTOList = attendanceList.stream().map(e -> attendanceMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return attendanceDTOList;
  }

  @Override
  public List<AttendanceDTO> getAttendanceForEmployee(Date dateFrom, Date dateTo) {
    List<Attendance> attendanceList = attendanceRepository.getAttendanceForEmployee(dateFrom, dateTo);
    List<AttendanceDTO> attendanceDTOList = attendanceList.stream().map(e -> attendanceMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return attendanceDTOList;
  }

  @Override
  public List<AttendanceDTO> getAttendanceForPayroll(Long payrollId) {
    List<Attendance> attendanceList = attendanceRepository.getAttendanceForPayrollId(payrollId);
    List<AttendanceDTO> attendanceDTOList = attendanceList.stream().map(e -> attendanceMapper.toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
    return attendanceDTOList;
  }
}
