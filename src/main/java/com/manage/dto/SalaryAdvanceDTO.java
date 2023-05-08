package com.manage.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SalaryAdvanceDTO extends AbstractNonAuditDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private Long salaryAdvanceId;

  private Double salaryAdvanceAmount;

  private Date salaryAdvanceDate;

  private EmployeeDTO employee;

}
