package com.manage.services.bl;

import com.manage.dto.AttendaceSaveRequestDTO;
import com.manage.model.Attendance;
import com.manage.model.Employee;
import com.manage.model.EmployeeShift;
import com.manage.model.Payroll;
import com.manage.model.Position;
import com.manage.model.SalaryDetail;
import com.manage.repository.EmployeeRepository;
import com.manage.services.AttendanceService;
import com.manage.services.EmployeeShiftService;
import com.manage.services.PayrollService;
import com.manage.services.PositionService;
import com.manage.services.SalaryDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaveAttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(GetEmployeeInforService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private AttendanceService attendanceService;

  @Autowired
  private PayrollService payrollService;

  @Autowired
  private SalaryDetailService salaryDetailService;

  @Autowired
  private PositionService positionService;

  @Autowired
  private EmployeeShiftService employeeShiftService;

  @Transactional
  public Boolean saveAttendance(AttendaceSaveRequestDTO attendaceSaveRequestDTO) throws SystemException {
    logger.debug("SaveAttendance ----> Param: ", attendaceSaveRequestDTO.toString());
    try {
      int month = attendaceSaveRequestDTO.getStartDateTime().getMonth() + 1;
      int year = attendaceSaveRequestDTO.getStartDateTime().getYear() + 1900;
      Payroll payrollSave = null;
      List<Payroll> payrollList = payrollService.getPayrollByMonthAndYearAndEmployeeId(month, year, attendaceSaveRequestDTO.getEmployeeId());
      if (payrollList == null || payrollList.size() == 0){
        Payroll payroll = new Payroll();
        payroll.setMonth(month);
        payroll.setYear(year);
        Employee employee = employeeRepository.findEmployeeByEmployeeId(attendaceSaveRequestDTO.getEmployeeId());
        payroll.setEmployee(employee);
        Date requiredDay = new Date(year, month - 1, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String requiredDayStr = formatter.format(requiredDay);
        List<SalaryDetail> salaryDetailDTOList = salaryDetailService.getCurrentSalaryDetailByPosition(requiredDayStr, employee.getPosition().getPositionId());
        payroll.setSalaryDetail(salaryDetailDTOList.get(0));
        Position position = positionService.getPositionById(employee.getPosition().getPositionId());
        payroll.setPosition(position);
        payroll.setBonus(0.0);
        payroll.setPaymentStatus(0);
        payrollSave = payrollService.save(payroll);
      } else {
        payrollSave = payrollList.get(0);
      }
      Attendance attendance = new Attendance();
      attendance.setStartDateTime(attendaceSaveRequestDTO.getStartDateTime());
      attendance.setEndDateTime(attendaceSaveRequestDTO.getEndDateTime());
      attendance.setPayroll(payrollSave);
      if (attendaceSaveRequestDTO.getEmployeeShiftId() != null) {
        EmployeeShift employeeShift= employeeShiftService.getEmployeeShiftById(attendaceSaveRequestDTO.getEmployeeShiftId());
        attendance.setEmployeeShift(employeeShift);
      }
      attendanceService.save(attendance);
      logger.debug("SaveAttendance ----> End: ", true);
      return true;
    } catch (Exception e) {
      logger.error("SaveAttendance ----> Error: ", e);
      throw new SystemException();
    }
  }

}
