package com.manage.dto;

import lombok.Data;

@Data
public class UpdatePayrollInforDTO {
  private Long salaryDetailId;
  private Double salary;
  private Double allowance;
}
