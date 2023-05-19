package com.manage.services.impl;

import com.manage.dto.SalaryDetailDTO;
import com.manage.model.SalaryDetail;
import com.manage.repository.SalaryDetailRepository;
import com.manage.services.SalaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryDetailServiceImpl implements SalaryDetailService {

  @Autowired
  private SalaryDetailRepository salaryDetailRepository;

  @Override
  public SalaryDetail save(SalaryDetail salaryDetail) {
    return salaryDetailRepository.save(salaryDetail);
  }

  @Override
  public List<SalaryDetail> getCurrentSalaryDetailByPosition(String requiredDay, Long positionId) {
    List<SalaryDetail> salaryDetailList = salaryDetailRepository.getCurrentSalaryDetailByPosition(requiredDay, positionId);
    return salaryDetailList;
  }

  @Override
  public List<SalaryDetailDTO> findAllCurrentSalaryDetail(String currentDay) {
    List<Object[]> list = salaryDetailRepository.findAllCurrentSalaryDetail(currentDay);
    List<SalaryDetailDTO> salaryDetailDTOList = list.stream().map(e -> new SalaryDetailDTO(e)).collect(Collectors.toList());
    return salaryDetailDTOList;
  }

  @Override
  public SalaryDetail getSalaryDetailBySalaryDetailId(Long salaryDetailId) {
    return salaryDetailRepository.getOne(salaryDetailId);
  }
}
