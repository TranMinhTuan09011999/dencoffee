package com.manage.services.bl;

import com.manage.dto.AttendanceDTO;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.dto.EmployeeDTO;
import com.manage.dto.EmployeePayrollDTO;
import com.manage.dto.PayrollDTO;
import com.manage.dto.SalaryAdvanceDTO;
import com.manage.services.EmployeeService;
import com.manage.services.PayrollService;
import com.manage.services.SalaryAdvanceService;
import com.manage.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class GetPayrollForMonthYearService {

  private static final Logger logger = LoggerFactory.getLogger(GetPayrollForMonthYearService.class);

  @Autowired
  private GetAttendanceForEmployeeService getAttendanceForEmployeeService;

  @Autowired
  private PayrollService payrollService;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private SalaryAdvanceService salaryAdvanceService;

  public List<EmployeePayrollDTO> getPayrollForMonthYear(Integer month, Integer year) throws SystemException {
    try {
      List<EmployeePayrollDTO> employeePayrollDTOList = new ArrayList<>();
      List<AttendanceDetailsForEmployeeDTO> attendanceDetailsForEmployeeDTOList =
              getAttendanceForEmployeeService.getAttendanceForMonthYear(month, year);
      if (Objects.nonNull(attendanceDetailsForEmployeeDTOList) && !attendanceDetailsForEmployeeDTOList.isEmpty()) {
        attendanceDetailsForEmployeeDTOList.forEach(item -> {
          EmployeePayrollDTO employeePayrollDTO = new EmployeePayrollDTO();
          employeePayrollDTO.setFullname(item.getFullname());

          EmployeeDTO employeeDTO = employeeService.getEmployeeByEmployeeId(item.getEmployeeId());
          if (Objects.nonNull(employeeDTO)) {
            if (Objects.nonNull(employeeDTO.getPosition())) {
              employeePayrollDTO.setPosition(employeeDTO.getPosition().getPositionName());
            }
          }

          double hourTotal = 0;
          if (Objects.nonNull(item.getAttendanceDTOList()) && !item.getAttendanceDTOList().isEmpty()) {
            for (int i=0; i<item.getAttendanceDTOList().size(); i++) {
              hourTotal += getHourTotalBetween2Time(item.getAttendanceDTOList().get(i).getStartDateTime(), item.getAttendanceDTOList().get(i).getEndDateTime());
            }
          }
          employeePayrollDTO.setHourTotal(hourTotal);

          PayrollDTO payrollDTO = getSalary(item, month, year);
          if (Objects.nonNull(payrollDTO)) {
            employeePayrollDTO.setHourSalary(payrollDTO.getSalary());
            Integer countHourTotalThan10 = getCountOfHourTotalThan10Hour(item);
            employeePayrollDTO.setAllowance(payrollDTO.getAllowance() * countHourTotalThan10);
            employeePayrollDTO.setBonus(payrollDTO.getBonus());
          }

          Double sundayHourTotal = getSundaySalary(item, month, year);
          employeePayrollDTO.setSundayHourTotal(sundayHourTotal);
          Double sundayHourSalaryAmount = 1000 * sundayHourTotal;
          employeePayrollDTO.setSundayBonus(sundayHourSalaryAmount);

          Double salaryAdvance = getSalaryAdvance(month, item.getEmployeeId());
          employeePayrollDTO.setSalaryAdvance(salaryAdvance);

          Double salaryAmount = employeePayrollDTO.getHourTotal() * employeePayrollDTO.getHourSalary() +
                  employeePayrollDTO.getAllowance() + employeePayrollDTO.getBonus();
          employeePayrollDTO.setSalaryAmount(salaryAmount);

          Double actualSalary = employeePayrollDTO.getSalaryAmount() - employeePayrollDTO.getSalaryAdvance();
          employeePayrollDTO.setActualSalary(actualSalary);

          Boolean paymentStatus = paymentStatus(item.getAttendanceDTOList());
          employeePayrollDTO.setPaymentStatus(paymentStatus);

          employeePayrollDTOList.add(employeePayrollDTO);
        });
      }
      return employeePayrollDTOList;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private double getHourTotalBetween2Time(Date startDateTime, Date endDateTime) {
    if (endDateTime != null) {
      var diff = endDateTime.getTime() - startDateTime.getTime();
      var hours = diff / (1000 * 60 * 60);
      var integerPart = Math.floor(hours);
      var decimalPart = (hours - integerPart) * 60;
      if (decimalPart >= 25 && decimalPart <= 55) {
        return integerPart + 0.5;
      } else if (decimalPart > 55) {
        return integerPart + 1;
      }
      return integerPart;
    }
    return 0;
  }

  private PayrollDTO getSalary(AttendanceDetailsForEmployeeDTO attendanceDetailsForEmployeeDTO, Integer month, Integer year) {
    Long position = attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(0).getPosition().getPositionId();
    List<PayrollDTO> payrollList = payrollService.getPayrollByPositionID(position);
    if (Objects.nonNull(payrollList) && !payrollList.isEmpty()) {
      DateTimeFormatter formatterForMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");
      String monthYearCur = (month < 10 ? "0" + month : month) + "-" + year;
      YearMonth monthYear = YearMonth.parse(monthYearCur, DateTimeFormatter.ofPattern("MM-yyyy"));
      for (int i=0; i<payrollList.size(); i++) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
        YearMonth monthYearStart = YearMonth.parse(formatter.format(payrollList.get(i).getStartDate()), formatterForMonthYear);
        boolean isBetween = false;
        if (payrollList.get(i).getEndDate() != null) {
          YearMonth monthYearEnd = YearMonth.parse(formatter.format(payrollList.get(i).getEndDate()), formatterForMonthYear);
          isBetween = monthYear.compareTo(monthYearStart) >= 0 && monthYear.compareTo(monthYearEnd) <= 0;
        } else {
          isBetween = monthYear.compareTo(monthYearStart) >= 0;
        }
        if (isBetween) {
          return payrollList.get(i);
        }
      }
    }
    return null;
  }

  private Integer getCountOfHourTotalThan10Hour(AttendanceDetailsForEmployeeDTO attendanceDetailsForEmployeeDTO) {
    Integer count = 0;
    for (int i=0; i<attendanceDetailsForEmployeeDTO.getAttendanceDTOList().size(); i++) {
      if (attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getEndDateTime() != null) {
        Double hourTotal = getHourTotalBetween2Time(attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getStartDateTime(),
                attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getEndDateTime());
        if (hourTotal > 10) {
          count++;
        }
      }
    }
    return count;
  }

  private Double getSundaySalary(AttendanceDetailsForEmployeeDTO attendanceDetailsForEmployeeDTO, Integer month, Integer year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, 1);

    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    Double hourTotal = 0.0;
    while (calendar.get(Calendar.MONTH) == month) {
      for (int i=0; i<attendanceDetailsForEmployeeDTO.getAttendanceDTOList().size(); i++) {
        if (attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getEndDateTime() != null) {
          if (DateUtils.compareDate(attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getStartDateTime(),
                  calendar.getTime())) {
            hourTotal += getHourTotalBetween2Time(attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getStartDateTime(),
                    attendanceDetailsForEmployeeDTO.getAttendanceDTOList().get(i).getEndDateTime());
          }
        }
      }
      calendar.add(Calendar.DAY_OF_MONTH, 7);
    }
    return hourTotal;
  }

  private Double getSalaryAdvance(Integer month, Long employeeId) {
    Double salaryAdvanceAmount = 0.0;
    List<SalaryAdvanceDTO> salaryAdvanceDTOList = salaryAdvanceService.getSalaryAdvanceByMonthAndEmployee(month, employeeId);
    if (Objects.nonNull(salaryAdvanceDTOList) && !salaryAdvanceDTOList.isEmpty()) {
      for (int i=0; i<salaryAdvanceDTOList.size(); i++) {
        salaryAdvanceAmount += salaryAdvanceDTOList.get(i).getSalaryAdvanceAmount();
      }
    }
    return salaryAdvanceAmount;
  }

  private Boolean paymentStatus(List<AttendanceDTO> attendanceDTOList) {
    Boolean paymentStatus = true;
    for (int i=0; i<attendanceDTOList.size(); i++) {
      if (attendanceDTOList.get(i).getEndDateTime() != null) {
        if (attendanceDTOList.get(i).getPayrollStatus() == 0) {
          paymentStatus = false;
        }
      }
    }
    return paymentStatus;
  }

}
