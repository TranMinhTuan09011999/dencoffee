package com.manage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AttendaceSaveRequestDTO {
  private Long employeeId;
  private Date startDateTime;
  private Date endDateTime;
  private Long employeeShiftId;
}
