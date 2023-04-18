package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.jsonview.AttendanceViews;
import com.manage.jsonview.PayrollViews;
import com.manage.util.FormatDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO extends AbstractNonAuditDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class,
          PayrollViews.PayrollViewForMonthYearSet.class})
  private Long attendanceId;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class,
          AttendanceViews.AttendanceDetailsForEmployeeViewSet.class,
          PayrollViews.PayrollViewForMonthYearSet.class})
  @JsonSerialize(using = FormatDateTimeSerializer.class)
  private Date startDateTime;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class,
          AttendanceViews.AttendanceDetailsForEmployeeViewSet.class,
          PayrollViews.PayrollViewForMonthYearSet.class})
  @JsonSerialize(using = FormatDateTimeSerializer.class)
  private Date endDateTime;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  private EmployeeDTO employee;

}
