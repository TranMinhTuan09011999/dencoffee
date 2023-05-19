package com.manage.services.bl;

import com.manage.dto.UpdatePayrollInforDTO;
import com.manage.model.Position;
import com.manage.model.SalaryDetail;
import com.manage.services.PositionService;
import com.manage.services.SalaryDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
public class UpdateSalaryDetailService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateSalaryDetailService.class);

  @Autowired
  private SalaryDetailService salaryDetailService;

  @Autowired
  private PositionService positionService;

  @Transactional
  public boolean updateSalaryDetail(UpdatePayrollInforDTO updatePayrollInforDTO) throws SystemException {
    try {
      SalaryDetail salaryDetail = salaryDetailService.getSalaryDetailBySalaryDetailId(updatePayrollInforDTO.getSalaryDetailId());
      if (Objects.isNull(salaryDetail.getEndDate())) {
        if (salaryDetail.getSalary() == updatePayrollInforDTO.getSalary()
                && salaryDetail.getAllowance() == updatePayrollInforDTO.getAllowance()) {
          return true;
        }
        Date today = new Date();
        DateTimeFormatter formatterForMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
        YearMonth monthYearToday = YearMonth.parse(formatter.format(today), formatterForMonthYear);
        YearMonth monthYearStartDate = YearMonth.parse(formatter.format(salaryDetail.getStartDate()), formatterForMonthYear);
        if (monthYearToday.compareTo(monthYearStartDate) == 0) {
          salaryDetail.setSalary(updatePayrollInforDTO.getSalary());
          salaryDetail.setAllowance(updatePayrollInforDTO.getAllowance());
          salaryDetailService.save(salaryDetail);
        } else if (monthYearToday.compareTo(monthYearStartDate) > 0) {
          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.DAY_OF_MONTH, 1);
          cal.add(Calendar.DATE, -1);
          Date endDate = cal.getTime();
          salaryDetail.setEndDate(endDate);
          salaryDetailService.save(salaryDetail);

          cal.add(Calendar.DATE, 1);
          Date newStartDate = cal.getTime();
          SalaryDetail newSalaryDetail = new SalaryDetail();
          newSalaryDetail.setStartDate(newStartDate);
          newSalaryDetail.setEndDate(null);
          newSalaryDetail.setSalary(updatePayrollInforDTO.getSalary());
          newSalaryDetail.setAllowance(updatePayrollInforDTO.getAllowance());
          Position position = positionService.getPositionById(salaryDetail.getPosition().getPositionId());
          newSalaryDetail.setPosition(position);
          salaryDetailService.save(newSalaryDetail);
        }
      }
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
