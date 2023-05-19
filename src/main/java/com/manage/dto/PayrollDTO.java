package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.AttendanceViews;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDTO extends AbstractNonAuditDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private Long payrollId;

  private Integer month;

  private Integer year;

  private Double bonus;

  private Integer paymentStatus;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  private EmployeeDTO employee;

  private SalaryDetailDTO salaryDetail;

  private List<AttendanceDTO> attendanceList;

  private PositionDTO position;

  private List<SalaryAdvanceDTO> salaryAdvanceList;

}
