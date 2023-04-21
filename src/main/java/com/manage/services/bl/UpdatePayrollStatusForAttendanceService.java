package com.manage.services.bl;

import com.manage.model.Attendance;
import com.manage.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.List;

@Service
public class UpdatePayrollStatusForAttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(UpdatePayrollStatusForAttendanceService.class);

  @Autowired
  private AttendanceRepository attendanceRepository;

  public boolean updatePayrollStatusForAttendance(Long employeeId, Integer month, Integer year) throws SystemException {
    try {
      List<Attendance> attendanceServiceList = attendanceRepository.getAttendanceForMonthYearAndEmployeeId(employeeId, month, year);
      attendanceServiceList.forEach(item -> {
        item.setPayrollStatus(1);
        attendanceRepository.save(item);
      });
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
