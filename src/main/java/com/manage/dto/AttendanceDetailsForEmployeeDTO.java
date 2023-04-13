package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.AttendanceViews;
import lombok.Data;

import java.util.List;

@Data
public class AttendanceDetailsForEmployeeDTO {
  @JsonView({AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  private Long employeeId;

  @JsonView({AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  private String fullname;

  @JsonView({AttendanceViews.AttendanceDetailsForEmployeeViewSet.class})
  private List<AttendanceDTO> attendanceDTOList;
}
