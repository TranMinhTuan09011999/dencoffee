package com.manage.services.bl;

import com.manage.dto.EmployeeDTO;
import com.manage.model.Employee;
import com.manage.model.Payroll;
import com.manage.model.Position;
import com.manage.model.WorkHistory;
import com.manage.repository.EmployeeRepository;
import com.manage.repository.PayrollRepository;
import com.manage.repository.PositionRepository;
import com.manage.repository.WorkHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class RegisterEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(GetEmployeeInforService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private WorkHistoryRepository workHistoryRepository;

  @Autowired
  private PayrollRepository payrollRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Transactional
  public Boolean registerEmployee(EmployeeDTO employeeDTO) throws SystemException {
    try {
      saveEmployee(employeeDTO);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void saveEmployee(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    employee.setFullname(employeeDTO.getFullname());
    employee.setGender(employeeDTO.getGender());
    employee.setBirthday(employeeDTO.getBirthday());
    employee.setPhoneNumber(employeeDTO.getPhoneNumber());
    employee.setAddress(employeeDTO.getAddress());
    employee.setStatus(employeeDTO.getStatus());
    Position position = positionRepository.getOne(employeeDTO.getPositionId());
    employee.setPosition(position);
    employee.setCreatedDate(new Date());
    employee.setCreatedBy("Admin");
    employee.setModifiedDate(new Date());
    employee.setModifiedBy("Admin");
    Employee employeeForWorkHistory = employeeRepository.save(employee);
    saveWorkHistory(employeeForWorkHistory);
  }

  private void saveWorkHistory(Employee employee) {
    WorkHistory workHistory = new WorkHistory();
    workHistory.setStartDate(new Date());
    workHistory.setEmployee(employee);
    workHistory.setCreatedDate(new Date());
    workHistory.setCreatedBy("Admin");
    workHistory.setModifiedDate(new Date());
    workHistory.setModifiedBy("Admin");
    workHistoryRepository.save(workHistory);
  }
}
