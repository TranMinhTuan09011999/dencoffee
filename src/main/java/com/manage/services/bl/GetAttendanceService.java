package com.manage.services.bl;

import com.manage.dto.AttendanceDTO;
import com.manage.services.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.Date;
import java.util.List;

@Service
public class GetAttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(GetEmployeeInforService.class);

  @Autowired
  private AttendanceService attendanceService;

  public List<AttendanceDTO> getAttendanceForToday(Date date) throws SystemException {
    try {
        return attendanceService.getAttendanceForToday(date);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
