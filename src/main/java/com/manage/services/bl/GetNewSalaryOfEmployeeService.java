package com.manage.services.bl;

import com.manage.model.PayRoll;
import com.manage.services.PayrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class GetNewSalaryOfEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(GetNewSalaryOfEmployeeService.class);

  @Autowired
  private PayrollService payrollService;

  public Double getNewSalaryOfEmployee(Long employeeId) throws SystemException {
    try {
      List<PayRoll> payRollList = payrollService.getPayrollByEmployee(employeeId);
      Double salary = null;
      for (int i=0; i<payRollList.size(); i++) {
        if (payRollList.get(i).getEndDate() == null) {
          YearMonth ym1 = YearMonth.of(payRollList.get(i).getStartDate().getYear(), payRollList.get(i).getStartDate().getMonth());
          Date today = new Date();
          YearMonth ym2 = YearMonth.of(today.getYear(), today.getMonth());
          int check = compareMonthYear(ym1, ym2);
          if (check == 2) {
            salary = payRollList.get(i).getSalary();
          }
        }
      }
      return salary;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private int compareMonthYear(YearMonth ym1, YearMonth ym2) {
    // So sánh hai đối tượng YearMonth
    if (ym1.isBefore(ym2)) {
      return 1;
    } else if (ym1.isAfter(ym2)) {
      return 2;
    } else {
      return 3;
    }
  }
}
