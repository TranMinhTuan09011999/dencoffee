package com.manage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceEndDateTimeUpdateDTO {
  private Long attendanceId;
  private Date endDateTime;
}
