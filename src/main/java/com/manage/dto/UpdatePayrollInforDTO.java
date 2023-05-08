package com.manage.dto;

import lombok.Data;

@Data
public class UpdatePayrollInforDTO {
  private Long payrollId;
  private Double salary;
  private Double allowance;
  private Double bonus;
}
