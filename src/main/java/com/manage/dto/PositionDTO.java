package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.EmployeeViews;
import com.manage.jsonview.PayrollViews;
import com.manage.jsonview.PositionViews;
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
          PayrollViews.PayrollViewForCurrentSet.class})
  private Long positionId;

  @JsonView({EmployeeViews.EmployeeViewSet.class,
          PositionViews.PositionViewsSet.class,
          PayrollViews.PayrollViewForCurrentSet.class})
  private String positionName;

  private List<PayrollDTO> payrollList;

  private List<EmployeeDTO> employeeList;

  private List<AttendanceDTO> attendanceList;
}
