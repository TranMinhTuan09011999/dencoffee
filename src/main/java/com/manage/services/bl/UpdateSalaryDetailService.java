package com.manage.services.bl;

import com.manage.dto.UpdatePayrollInforDTO;
import com.manage.model.Payroll;
import com.manage.model.Position;
import com.manage.model.SalaryDetail;
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
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Service
public class UpdateSalaryDetailService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateSalaryDetailService.class);

  @Autowired
  private SalaryDetailService salaryDetailService;

  @Autowired
  private PositionService positionService;

  @Autowired
  private PayrollService payrollService;

  @Transactional
  public boolean updateSalaryDetail(UpdatePayrollInforDTO updatePayrollInforDTO) throws SystemException {
    try {
      SalaryDetail salaryDetail = salaryDetailService.getSalaryDetailBySalaryDetailId(updatePayrollInforDTO.getSalaryDetailId());
      if (Objects.isNull(salaryDetail.getEndDate())) {
        if (salaryDetail.getSalary() == updatePayrollInforDTO.getSalary()
                && salaryDetail.getAllowance() == updatePayrollInforDTO.getAllowance()) {
          return true;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String startDateStr = format.format(salaryDetail.getStartDate());
        String[] dates = startDateStr.split("-");
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth targetYearMonth = YearMonth.of(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]));
        int comparisonResult = currentYearMonth.compareTo(targetYearMonth);
        if (comparisonResult == 0) {
          salaryDetail.setSalary(updatePayrollInforDTO.getSalary());
          salaryDetail.setAllowance(updatePayrollInforDTO.getAllowance());
          salaryDetailService.save(salaryDetail);
        } else if (comparisonResult > 0){
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
          SalaryDetail newSalaryDetailForPosition = salaryDetailService.save(newSalaryDetail);

          updatePayrollForEmployee(currentYearMonth.getMonthValue(), currentYearMonth.getYear(),
                  salaryDetail.getPosition().getPositionId(), newSalaryDetailForPosition);
        }
      }
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void updatePayrollForEmployee(int month, int year, Long positionId, SalaryDetail salaryDetail) {
    List<Payroll> payrollList = payrollService.getPayrollByMonthAndYearAndPositionId(month, year, positionId);
    if (Objects.nonNull(payrollList) && !payrollList.isEmpty()) {
      payrollList.forEach(item -> {
        item.setSalaryDetail(salaryDetail);
        payrollService.save(item);
      });
    }
  }

}
