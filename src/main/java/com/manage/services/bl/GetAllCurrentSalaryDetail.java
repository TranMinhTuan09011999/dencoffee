package com.manage.services.bl;

import com.manage.dto.SalaryDetailDTO;
import com.manage.services.SalaryDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GetAllCurrentSalaryDetail {

  private static final Logger logger = LoggerFactory.getLogger(GetAllCurrentSalaryDetail.class);

  @Autowired
  private SalaryDetailService salaryDetailService;

  public List<SalaryDetailDTO> getAllCurrentSalaryDetail() throws SystemException {
    try {
      Date today = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String currentDay = formatter.format(today);
      return salaryDetailService.findAllCurrentSalaryDetail(currentDay);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
