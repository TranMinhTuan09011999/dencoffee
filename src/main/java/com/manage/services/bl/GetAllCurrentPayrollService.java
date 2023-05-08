package com.manage.services.bl;

import com.manage.dto.PayrollDTO;
import com.manage.services.PayrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GetAllCurrentPayrollService {

  private static final Logger logger = LoggerFactory.getLogger(GetAllCurrentPayrollService.class);

  @Autowired
  private PayrollService payrollService;

  public List<PayrollDTO> getAllCurrentPayroll() throws SystemException {
    try {
      Date today = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String currentDay = formatter.format(today);
      return payrollService.findAllCurrentPayroll(currentDay);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
