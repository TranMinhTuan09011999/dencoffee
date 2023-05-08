package com.manage.services.bl;

import com.manage.dto.EmployeeDTO;
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
      return employeeDTOList;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
