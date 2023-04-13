package com.manage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceForEmployeeRequestDTO {
  private Date dateFrom;
  private Date dateTo;
}
