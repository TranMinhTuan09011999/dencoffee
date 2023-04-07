package com.manage.mapper;

import com.manage.dto.AttendanceDTO;
import com.manage.model.Attendance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceMapper extends AbstractMapper<AttendanceDTO, Attendance> {
}
