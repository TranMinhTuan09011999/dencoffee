package com.manage.services.bl;

import com.manage.dto.AttendanceEndDateTimeUpdateDTO;
import com.manage.model.Attendance;
import com.manage.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;

@Service
public class UpdateAttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateAttendanceService.class);

  @Autowired
  private AttendanceRepository attendanceRepository;

  public Boolean updateEndDateTimeForAttendance(AttendanceEndDateTimeUpdateDTO attendanceEndDateTimeUpdateDTO) throws SystemException {
    try {
      updateEndDateTime(attendanceEndDateTimeUpdateDTO);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void updateEndDateTime(AttendanceEndDateTimeUpdateDTO attendanceEndDateTimeUpdateDTO) {
    Attendance attendance = attendanceRepository.getOne(attendanceEndDateTimeUpdateDTO.getAttendanceId());
    attendance.setEndDateTime(attendanceEndDateTimeUpdateDTO.getEndDateTime());
    attendance.setPayrollStatus(0);
    attendanceRepository.save(attendance);
  }

}
