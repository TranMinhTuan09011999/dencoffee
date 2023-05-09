package com.manage.services.bl;

import com.manage.dto.SalaryAdvanceDTO;
import com.manage.model.Employee;
import com.manage.model.SalaryAdvance;
import com.manage.services.EmployeeService;
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
  private EmployeeService employeeService;

  @Autowired
  private SalaryAdvanceService salaryAdvanceService;

  public Boolean advanceEmployeeSalary(Long employeeId, Double salaryAdvanceAmount) throws SystemException {
    try {
      SalaryAdvance salaryAdvance = new SalaryAdvance();
      salaryAdvance.setSalaryAdvanceDate(new Date());
      salaryAdvance.setSalaryAdvanceAmount(salaryAdvanceAmount);
      Employee employee = employeeService.getEmployeeById(employeeId);
      salaryAdvance.setEmployee(employee);
      salaryAdvanceService.save(salaryAdvance);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  public List<SalaryAdvanceDTO> getSalaryAdvanceByEmployee(Integer month, Integer year, Long employeeId) throws SystemException {
    try {
      return salaryAdvanceService.getSalaryAdvanceByMonthAndEmployee(month, year, employeeId);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
