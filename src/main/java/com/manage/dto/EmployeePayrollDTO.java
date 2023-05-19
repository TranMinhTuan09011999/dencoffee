package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.PayrollViews;
import lombok.Data;

import java.util.List;

@Data
public class EmployeePayrollDTO {

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Long payrollId;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Long employeeId;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private String fullname;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private String position;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double hourTotal;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double hourSalary;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double allowance;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double bonus;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double sundayHourTotal;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double sundayBonus;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double salaryAdvance;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double salaryAmount;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Double actualSalary;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private Boolean paymentStatus;

  @JsonView({PayrollViews.PayrollViewForMonthYearSet.class})
  private List<AttendanceDTO> attendanceDTOList;
}