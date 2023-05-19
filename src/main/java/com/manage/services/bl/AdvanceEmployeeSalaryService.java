package com.manage.services.bl;

import com.manage.dto.SalaryAdvanceDTO;
import com.manage.model.Payroll;
import com.manage.model.SalaryAdvance;
import com.manage.services.PayrollService;
import com.manage.services.SalaryAdvanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.Date;
import java.util.List;

@Service
public class AdvanceEmployeeSalaryService {

  private static final Logger logger = LoggerFactory.getLogger(AdvanceEmployeeSalaryService.class);

  @Autowired
  private PayrollService payrollService;

  @Autowired
  private SalaryAdvanceService salaryAdvanceService;

  public Boolean advanceEmployeeSalary(Long payrollId, Double salaryAdvanceAmount) throws SystemException {
    try {
      SalaryAdvance salaryAdvance = new SalaryAdvance();
      salaryAdvance.setSalaryAdvanceDate(new Date());
      salaryAdvance.setSalaryAdvanceAmount(salaryAdvanceAmount);
      Payroll payroll = payrollService.getPayrollByPayrollId(payrollId);
      salaryAdvance.setPayroll(payroll);
      salaryAdvanceService.save(salaryAdvance);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  public List<SalaryAdvanceDTO> getSalaryAdvanceByPayroll(Long payrollId) throws SystemException {
    try {
      return salaryAdvanceService.getSalaryAdvanceByPayroll(payrollId);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
