package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.jsonview.AttendanceViews;
import com.manage.model.Position;
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

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  private Long attendanceId;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class,
          AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  @JsonSerialize(using = FormatDateTimeSerializer.class)
  private Date startDateTime;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class,
          AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  @JsonSerialize(using = FormatDateTimeSerializer.class)
  private Date endDateTime;

  private Integer payrollStatus;

  @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
  private EmployeeDTO employee;

  private PositionDTO position;

}
