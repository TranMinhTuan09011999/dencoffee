package com.manage.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AttendanceUpdateDTO {

    private Long attendanceId;
    private Date startDateTime;
    private Date endDateTime;

}
