package com.manage.services.bl;

import com.manage.dto.UpdatePayrollInforDTO;
import com.manage.model.Payroll;
import com.manage.model.Position;
import com.manage.services.PayrollService;
import com.manage.services.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
public class UpdatePayrollService {

  private static final Logger logger = LoggerFactory.getLogger(UpdatePayrollService.class);

  @Autowired
  private PayrollService payrollService;

  @Autowired
  private PositionService positionService;

  public boolean updatePayroll(UpdatePayrollInforDTO updatePayrollInforDTO) throws SystemException {
    try {
      Payroll payroll = payrollService.getPayrollByPayrollId(updatePayrollInforDTO.getPayrollId());
      if (Objects.isNull(payroll.getEndDate())) {
        if (payroll.getSalary() == updatePayrollInforDTO.getSalary()
                && payroll.getAllowance() == updatePayrollInforDTO.getAllowance()
                && payroll.getBonus() == updatePayrollInforDTO.getBonus()) {
          return true;
        }
        Date today = new Date();
        DateTimeFormatter formatterForMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
        YearMonth monthYearToday = YearMonth.parse(formatter.format(today), formatterForMonthYear);
        YearMonth monthYearStartDate = YearMonth.parse(formatter.format(payroll.getStartDate()), formatterForMonthYear);
        if (monthYearToday.compareTo(monthYearStartDate) == 0) {
          payroll.setSalary(updatePayrollInforDTO.getSalary());
          payroll.setAllowance(updatePayrollInforDTO.getAllowance());
          payroll.setBonus(updatePayrollInforDTO.getBonus());
          payrollService.save(payroll);
        } else if (monthYearToday.compareTo(monthYearStartDate) > 0) {
          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.DAY_OF_MONTH, 1);
          cal.add(Calendar.DATE, -1);
          Date endDate = cal.getTime();
          payroll.setEndDate(endDate);
          payrollService.save(payroll);

          cal.add(Calendar.DATE, 1);
          Date newStartDate = cal.getTime();
          Payroll newPayroll = new Payroll();
          newPayroll.setStartDate(newStartDate);
          newPayroll.setEndDate(null);
          newPayroll.setSalary(updatePayrollInforDTO.getSalary());
          newPayroll.setAllowance(updatePayrollInforDTO.getAllowance());
          newPayroll.setBonus(updatePayrollInforDTO.getBonus());
          Position position = positionService.getPositionById(payroll.getPosition().getPositionId());
          newPayroll.setPosition(position);
          payrollService.save(newPayroll);
        }
      }
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
