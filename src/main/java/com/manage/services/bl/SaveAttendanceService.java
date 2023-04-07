package com.manage.services.bl;

import com.manage.dto.AttendaceSaveRequestDTO;
import com.manage.model.Attendance;
import com.manage.model.Employee;
import com.manage.repository.AttendanceRepository;
import com.manage.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;

@Service
public class SaveAttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(GetEmployeeInforService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private AttendanceRepository attendanceRepository;

  public Boolean saveAttendance(AttendaceSaveRequestDTO attendaceSaveRequestDTO) throws SystemException {
    try {
      Attendance attendance = new Attendance();
      attendance.setStartDateTime(attendaceSaveRequestDTO.getStartDateTime());
      Employee employee = employeeRepository.findEmployeeByEmployeeId(attendaceSaveRequestDTO.getEmployeeId());
      attendance.setEmployee(employee);
      attendanceRepository.save(attendance);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
