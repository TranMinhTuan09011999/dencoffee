package com.manage.services.bl;

import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.model.PayRoll;
import com.manage.services.PayrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GetPayrollForMonthYearService {

  private static final Logger logger = LoggerFactory.getLogger(GetPayrollForMonthYearService.class);

  @Autowired
  private GetAttendanceForEmployeeService getAttendanceForEmployeeService;

  @Autowired
  private PayrollService payrollService;

  public List<AttendanceDetailsForEmployeeDTO> GetPayrollForMonthYear(Integer month, Integer year) throws SystemException {
    try {
      List<AttendanceDetailsForEmployeeDTO> attendanceDetailsForEmployeeDTOList =
              getAttendanceForEmployeeService.getAttendanceForMonthYear(month, year);
      attendanceDetailsForEmployeeDTOList.forEach(item -> {
        List<PayRoll> payRollList = payrollService.getPayrollByEmployee(item.getEmployeeId());
        DateTimeFormatter formatterForMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");
        String monthYearCur = (month < 10 ? "0" + month : month) + "-" + year;
        YearMonth monthYear = YearMonth.parse(monthYearCur, DateTimeFormatter.ofPattern("MM-yyyy"));
        List<PayRoll> payRollListForMonthYearList = new ArrayList<>();
        payRollList.forEach(item1 -> {
          SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
          YearMonth monthYearStart = YearMonth.parse(formatter.format(item1.getStartDate()), formatterForMonthYear);
          boolean isBetween = false;
          if (item1.getEndDate() != null) {
            YearMonth monthYearEnd = YearMonth.parse(formatter.format(item1.getEndDate()), formatterForMonthYear);
            isBetween = monthYear.compareTo(monthYearStart) >= 0 && monthYear.compareTo(monthYearEnd) <= 0;
          } else {
            isBetween = monthYear.compareTo(monthYearStart) >= 0;
          }
          if (isBetween) {
            payRollListForMonthYearList.add(item1);
          }
        });
        if (Objects.nonNull(payRollListForMonthYearList) && !payRollListForMonthYearList.isEmpty()) {
          item.setCurrentSalary(payRollListForMonthYearList.get(0).getSalary());
        }
      });
      return attendanceDetailsForEmployeeDTOList;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }
}
