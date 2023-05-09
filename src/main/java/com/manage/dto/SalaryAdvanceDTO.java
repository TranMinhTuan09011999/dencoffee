package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.jsonview.SalaryAdvanceViews;
import com.manage.util.FormatDateSerializer;
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

  @JsonView({SalaryAdvanceViews.GetSalaryAdvanceViewsSet.class})
  private Double salaryAdvanceAmount;

  @JsonView({SalaryAdvanceViews.GetSalaryAdvanceViewsSet.class})
  @JsonSerialize(using = FormatDateSerializer.class)
  private Date salaryAdvanceDate;

  private EmployeeDTO employee;

}
