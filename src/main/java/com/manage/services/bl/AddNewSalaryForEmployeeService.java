package com.manage.services.bl;

import com.manage.model.Employee;
import com.manage.model.PayRoll;
import com.manage.repository.EmployeeRepository;
import com.manage.repository.PayrollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AddNewSalaryForEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(CheckIpAddressService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PayrollRepository payrollRepository;

  public Boolean addNewSalaryForEmployee(Long employeeId, Double newSalary) throws SystemException {
    try {
      return updateEndTimeForOldSalary(employeeId, newSalary);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private Boolean updateEndTimeForOldSalary(Long employeeId, Double newSalary) {
    List<PayRoll> payRollList = payrollRepository.findAllByEmployeeId(employeeId);
    payRollList.forEach(item -> {
      if (item.getEndDate() == null) {
        YearMonth ym1 = YearMonth.of(item.getStartDate().getYear(), item.getStartDate().getMonth());
        Date today = new Date();
        YearMonth ym2 = YearMonth.of(today.getYear(), today.getMonth());
        int check = compareMonthYear(ym1, ym2);
        if (check == 2) {
          item.setSalary(newSalary);
          payrollRepository.save(item);
          return ;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        int lastDateOfMonth = calendar.get(Calendar.DATE);
        int lastMonthOfYear = calendar.get(Calendar.MONTH); // Vì tháng tính từ 0 đến 11 nên cần cộng thêm 1
        int lastYear = calendar.get(Calendar.YEAR);
        Date lastDateOfCurrentMonth = new Date(lastYear - 1900, lastMonthOfYear, lastDateOfMonth);
        item.setEndDate(lastDateOfCurrentMonth);
        payrollRepository.save(item);
        addNewSalary(employeeId, newSalary);      }
    });
    return true;
  }

  private void addNewSalary(Long employeeId, Double newSalary) {
    PayRoll payRoll = new PayRoll();
    Date today = new Date();
    Date nextMonth = new Date(today.getYear(), today.getMonth() + 1, 1);
    payRoll.setStartDate(nextMonth);
    Employee employee = employeeRepository.getOne(employeeId);
    payRoll.setEmployee(employee);
    payRoll.setEndDate(null);
    payRoll.setSalary(newSalary);
    payrollRepository.save(payRoll);
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
