package com.manage.services;

import com.manage.dto.SalaryDetailDTO;
import com.manage.model.SalaryDetail;

import java.util.List;

public interface SalaryDetailService {
  SalaryDetail save(SalaryDetail salaryDetail);
  List<SalaryDetail> getCurrentSalaryDetailByPosition(String requiredDay, Long positionId );
  List<SalaryDetailDTO> findAllCurrentSalaryDetail(String currentDay);
  SalaryDetail getSalaryDetailBySalaryDetailId(Long SalaryDetailId);
}
