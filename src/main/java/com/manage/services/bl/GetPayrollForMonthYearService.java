package com.manage.services.bl;

import com.manage.dto.AttendanceDTO;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.dto.EmployeePayrollDTO;
import com.manage.dto.PayrollDTO;
import com.manage.dto.SalaryAdvanceDTO;
import com.manage.model.Payroll;
import com.manage.services.AttendanceService;
import com.manage.services.EmployeeService;
import com.manage.services.PayrollService;
import com.manage.services.SalaryAdvanceService;
import com.manage.services.SalaryDetailService;
import com.manage.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.time.LocalDate;
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
  private SalaryAdvanceService salaryAdvanceService;

  @Autowired
  private AttendanceService attendanceService;

  public List<EmployeePayrollDTO> getPayrollForMonthYear(Integer month, Integer year) throws SystemException {
    try {
      List<EmployeePayrollDTO> employeePayrollDTOList = new ArrayList<>();
      List<PayrollDTO> payrollDTOList = payrollService.getPayrollByMonthAndYear(month, year);
      if (Objects.nonNull(payrollDTOList) && !payrollDTOList.isEmpty()) {
        payrollDTOList.forEach(item -> {
          EmployeePayrollDTO employeePayrollDTO = new EmployeePayrollDTO();
          employeePayrollDTO.setEmployeeId(item.getEmployee().getEmployeeId());
          employeePayrollDTO.setFullname(item.getEmployee().getFullname());
          employeePayrollDTO.setPosition(item.getPosition().getPositionName());

          List<AttendanceDTO> attendanceDTOList = attendanceService.getAttendanceForPayroll(item.getPayrollId());
          double hourTotal = 0;
          if (Objects.nonNull(attendanceDTOList) && !attendanceDTOList.isEmpty()) {
            for (int i=0; i<attendanceDTOList.size(); i++) {
              hourTotal += getHourTotalBetween2Time(attendanceDTOList.get(i).getStartDateTime(), attendanceDTOList.get(i).getEndDateTime());
            }
          }
          employeePayrollDTO.setHourTotal(hourTotal);
          employeePayrollDTO.setPayrollId(item.getPayrollId());
          employeePayrollDTO.setHourSalary(item.getSalaryDetail().getSalary());

          Integer countHourTotalThan10 = getCountOfHourTotalThan10Hour(attendanceDTOList, month, year);
          employeePayrollDTO.setAllowance(item.getSalaryDetail().getAllowance() * countHourTotalThan10);

          employeePayrollDTO.setBonus(item.getBonus());

          Double sundayHourTotal = getSundaySalary(attendanceDTOList, month, year);
          employeePayrollDTO.setSundayHourTotal(sundayHourTotal);

          Double sundayHourSalaryAmount = 1000 * sundayHourTotal;
          employeePayrollDTO.setSundayBonus(sundayHourSalaryAmount);

          Double salaryAdvance = getSalaryAdvance(item.getPayrollId());
          employeePayrollDTO.setSalaryAdvance(salaryAdvance);

          Double salaryAmount = employeePayrollDTO.getHourTotal() * employeePayrollDTO.getHourSalary() +
                  employeePayrollDTO.getAllowance() + employeePayrollDTO.getBonus() + employeePayrollDTO.getSundayBonus();
          employeePayrollDTO.setSalaryAmount(salaryAmount);

          Double actualSalary = employeePayrollDTO.getSalaryAmount() - employeePayrollDTO.getSalaryAdvance();
          employeePayrollDTO.setActualSalary(actualSalary);

          Boolean paymentStatus = paymentStatus(item.getPaymentStatus());
          employeePayrollDTO.setPaymentStatus(paymentStatus);

          employeePayrollDTO.setAttendanceDTOList(attendanceDTOList);

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
      long diff = endDateTime.getTime() - startDateTime.getTime();
      double hours = (double) diff / (1000*60*60);
      double integerPart = Math.floor(hours);
      var decimalPart = (hours - integerPart) * 60;
      if (decimalPart >= 25 && decimalPart < 55) {
        return integerPart + 0.5;
      } else if (decimalPart >= 55) {
        return integerPart + 1;
      }
      return integerPart;
    }
    return 0;
  }

  private Integer getCountOfHourTotalThan10Hour(List<AttendanceDTO> attendanceDTOList, Integer month, Integer year) {
    LocalDate date = LocalDate.of(year, month, 1);
    int daysInMonth = date.lengthOfMonth();
    Integer count = 0;

    for (int i = 0; i < daysInMonth; i++) {
      double hourTotalOfDay = getHourTotalOfDay(i+1, attendanceDTOList);
      if (hourTotalOfDay > 10) {
        count++;
      }
    }
    return count;
  }

  private Double getSundaySalary(List<AttendanceDTO> attendanceDTOList, Integer month, Integer year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month - 1, 1);

    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    Double hourTotal = 0.0;
    while (calendar.get(Calendar.MONTH) == month - 1) {
      for (int i=0; i<attendanceDTOList.size(); i++) {
        if (attendanceDTOList.get(i).getEndDateTime() != null) {
          if (DateUtils.compareDate(attendanceDTOList.get(i).getStartDateTime(),
                  calendar.getTime())) {
            hourTotal += getHourTotalBetween2Time(attendanceDTOList.get(i).getStartDateTime(),
                    attendanceDTOList.get(i).getEndDateTime());
          }
        }
      }
      calendar.add(Calendar.DAY_OF_MONTH, 7);
    }
    return hourTotal;
  }

  private Double getSalaryAdvance(Long payrolllId) {
    Double salaryAdvanceAmount = 0.0;
    List<SalaryAdvanceDTO> salaryAdvanceDTOList = salaryAdvanceService.getSalaryAdvanceByPayroll(payrolllId);
    if (Objects.nonNull(salaryAdvanceDTOList) && !salaryAdvanceDTOList.isEmpty()) {
      for (int i=0; i<salaryAdvanceDTOList.size(); i++) {
        salaryAdvanceAmount += salaryAdvanceDTOList.get(i).getSalaryAdvanceAmount();
      }
    }
    return salaryAdvanceAmount;
  }

  private Boolean paymentStatus(Integer paymentStatus) {
    if (paymentStatus == 0) {
      return false;
    } else {
      return true;
    }
  }

  private double getHourTotalOfDay(Integer day, List<AttendanceDTO> attendanceDTOList) {
    if (Objects.nonNull(attendanceDTOList) && !attendanceDTOList.isEmpty()) {
      double hourTotal = attendanceDTOList.stream().filter(item -> item.getStartDateTime().getDate() == day)
              .mapToDouble(item -> getHourTotalBetween2Time(item.getStartDateTime(), item.getEndDateTime())).sum();
      return hourTotal;
    }
    return 0;
  }

}
