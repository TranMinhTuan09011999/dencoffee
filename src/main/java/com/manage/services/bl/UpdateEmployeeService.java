package com.manage.services.bl;

import com.manage.dto.EmployeeDTO;
import com.manage.model.Attendance;
import com.manage.model.Employee;
import com.manage.model.Position;
import com.manage.repository.AttendanceRepository;
import com.manage.repository.EmployeeRepository;
import com.manage.services.AttendanceService;
import com.manage.services.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class UpdateEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateEmployeeService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PositionService positionService;

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Transactional
  public Boolean updateEmployeeById(EmployeeDTO employeeDTO) throws SystemException {
    try {
      updateEmployee(employeeDTO);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void updateEmployee(EmployeeDTO employeeDTO) {
    Employee employee = employeeRepository.getOne(employeeDTO.getEmployeeId());
    employee.setEmployeeId(employeeDTO.getEmployeeId());
    employee.setFullname(employeeDTO.getFullname());
    employee.setGender(employeeDTO.getGender());
    employee.setBirthday(employeeDTO.getBirthday());
    employee.setPhoneNumber(employeeDTO.getPhoneNumber());
    employee.setAddress(employeeDTO.getAddress());
    Position position = positionService.getPositionById(employeeDTO.getPositionId());
    employee.setPosition(position);
    employee.setModifiedDate(new Date());
    employee.setModifiedBy("Admin");
    employeeRepository.save(employee);
    updatePositionForEmployeeAttendance(employeeDTO.getEmployeeId(), position);
  }

  private void updatePositionForEmployeeAttendance(Long employeeId, Position position) {
    var today = LocalDate.now();;
    List<Attendance> attendanceServiceList = attendanceRepository.getAttendanceForMonthYearAndEmployeeId(employeeId, today.getMonthValue(), today.getYear());
    attendanceServiceList.forEach(item -> {
      item.setPosition(position);
      attendanceRepository.save(item);
    });
  }

}
