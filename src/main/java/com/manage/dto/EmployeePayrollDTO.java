package com.manage.dto;

import lombok.Data;

@Data
public class EmployeePayrollDTO {

  private String fullname;
  private String position;
  private Double hourTotal;
  private Double hourSalary;
  private Double allowance;
  private Double bonus;
  private Double sundayHourTotal;
  private Double sundayBonus;
  private Double salaryAdvance;
  private Double salaryAmount;
  private Double actualSalary;
  private Boolean paymentStatus;
}