package com.manage.services.bl;

import com.manage.dto.EmployeeDTO;
import com.manage.model.PayRoll;
import com.manage.services.EmployeeService;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class GetEmployeeInforService {

  private static final Logger logger = LoggerFactory.getLogger(GetEmployeeInforService.class);

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private PayrollService payrollService;

  public List<EmployeeDTO> getEmployeeInforByStatus(Integer status) throws SystemException {
    try {
      List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeeByStatus(status);
      employeeDTOList.forEach(item -> {
        List<PayRoll> payRollList = payrollService.getPayrollByEmployee(item.getEmployeeId());
        Date today = new Date();
        int month = today.getMonth() + 1;
        DateTimeFormatter formatterForMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");
        String monthYearCur = (month < 10 ? "0" + month : month) + "-" + (today.getYear() + 1900);
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
          item.setSalary(payRollListForMonthYearList.get(0).getSalary());
        }
      });

      return employeeDTOList;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
