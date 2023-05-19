package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.EmployeeViews;
import com.manage.jsonview.PositionViews;
import com.manage.jsonview.SalaryDetailViews;
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
public class PositionDTO extends AbstractNonAuditDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({EmployeeViews.EmployeeViewSet.class,
          PositionViews.PositionViewsSet.class,
          SalaryDetailViews.currentSalaryDetailViewsSet.class})
  private Long positionId;

  @JsonView({EmployeeViews.EmployeeViewSet.class,
          PositionViews.PositionViewsSet.class,
          SalaryDetailViews.currentSalaryDetailViewsSet.class})
  private String positionName;

  private List<SalaryDetailDTO> salaryDetailList;

  private List<EmployeeDTO> employeeList;

  private List<AttendanceDTO> attendanceList;

  private List<PayrollDTO> payrollList;
}
