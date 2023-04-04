package com.manage.services.bl;

import com.manage.model.Employee;
import com.manage.model.WorkHistory;
import com.manage.repository.EmployeeRepository;
import com.manage.repository.WorkHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UpdateStatusForEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateStatusForEmployeeService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private WorkHistoryRepository workHistoryRepository;

  public Boolean updateStatusForEmployee(Long employeeId, Integer status) throws SystemException {
    try {
      updateStatus(employeeId, status);
      if (status == 1) {
        saveNewWorkHistory(employeeId);
      } else {
        updateWorkHistory(employeeId);
      }
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void updateStatus(Long employeeId, Integer status) {
    Employee employee = employeeRepository.findEmployeeByEmployeeId(employeeId);
    employee.setStatus(status);
    employee.setModifiedDate(new Date());
    employee.setModifiedBy("Admin");
    employeeRepository.save(employee);
  }

  private void updateWorkHistory(Long employeeId) {
    List<WorkHistory> workHistoryList = workHistoryRepository.getWorkHistoryByEmployeeId(employeeId);
    if (Objects.nonNull(workHistoryList) && !workHistoryList.isEmpty()) {
      WorkHistory workHistory = workHistoryList.get(workHistoryList.size() - 1);
      workHistory.setEndDate(new Date());
      workHistory.setModifiedDate(new Date());
      workHistory.setModifiedBy("Admin");
      workHistoryRepository.save(workHistory);
    }
  }

  private void saveNewWorkHistory(Long employeeId) {
    WorkHistory workHistory = new WorkHistory();
    workHistory.setStartDate(new Date());
    Employee employeeForWorkHistory = employeeRepository.findEmployeeByEmployeeId(employeeId);
    workHistory.setEmployee(employeeForWorkHistory);
    workHistory.setCreatedDate(new Date());
    workHistory.setCreatedBy("Admin");
    workHistory.setModifiedDate(new Date());
    workHistory.setModifiedBy("Admin");
    workHistoryRepository.save(workHistory);
  }

}
