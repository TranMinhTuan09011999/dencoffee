package com.manage.dto;

import lombok.Data;

@Data
public class SalaryAdvanceRequestDTO {
  private Long employeeId;
  private Double salaryAdvanceAmount;
}
