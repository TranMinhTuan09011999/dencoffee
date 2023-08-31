package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.AttendanceViews;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeShiftDTO extends AbstractNonAuditDTO {

    @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
    private Long employeeShiftId;

    @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
    private String employeeShiftName;

    @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
    private String startTime;

    @JsonView({AttendanceViews.AttendanceForTodayViewSet.class})
    private String endTime;

    private List<AttendanceDTO> attendanceDTOList;

}
