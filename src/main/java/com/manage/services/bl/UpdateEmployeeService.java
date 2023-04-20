package com.manage.services.bl;

import com.manage.dto.EmployeeDTO;
import com.manage.model.Employee;
import com.manage.model.PayRoll;
import com.manage.repository.EmployeeRepository;
import com.manage.repository.PayrollRepository;
import com.manage.services.PayrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class UpdateEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateEmployeeService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PayrollService payrollService;

  @Autowired
  private PayrollRepository payrollRepository;

  public Boolean updateEmployeeById(EmployeeDTO employeeDTO) throws SystemException {
    try {
      updateEmployee(employeeDTO);
      updateCurrentSalary(employeeDTO.getEmployeeId(), employeeDTO.getSalary());
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void updateEmployee(EmployeeDTO employeeDTO) {
    Employee employee = employeeRepository.getOne(employeeDTO.getEmployeeId());
    employee.setEmployeeId(employeeDTO.getEmployeeId());
    employee.setFullname(employeeDTO.getFullname());
    employee.setGender(employeeDTO.getGender());
    employee.setBirthday(employeeDTO.getBirthday());
    employee.setPhoneNumber(employeeDTO.getPhoneNumber());
    employee.setAddress(employeeDTO.getAddress());
    employee.setModifiedDate(new Date());
    employee.setModifiedBy("Admin");
    employeeRepository.save(employee);
  }

  private void updateCurrentSalary(Long employeeId, Double salary) {
    List<PayRoll> payRollList = payrollService.getPayrollByEmployee(employeeId);
    Date today = new Date();
    int month = today.getMonth() + 1;
    DateTimeFormatter formatterForMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");
    String monthYearCur = (month < 10 ? "0" + month : month) + "-" + (today.getYear() + 1900);
    YearMonth monthYear = YearMonth.parse(monthYearCur, DateTimeFormatter.ofPattern("MM-yyyy"));
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
        item1.setSalary(salary);
        payrollRepository.save(item1);
      }
    });
  }

}
