package com.manage.services.bl;

import com.manage.dto.AttendanceDTO;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import com.manage.dto.AttendanceForEmployeeRequestDTO;
import com.manage.services.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GetAttendanceForEmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(GetAttendanceForEmployeeService.class);

  @Autowired
  private AttendanceService attendanceService;

  public List<AttendanceDetailsForEmployeeDTO> getAttendanceForEmployee(AttendanceForEmployeeRequestDTO attendanceForEmployeeRequestDTO) throws SystemException {
    try {
      List<AttendanceDTO> attendanceDTOList = attendanceService.getAttendanceForEmployee(attendanceForEmployeeRequestDTO.getDateFrom(),
              attendanceForEmployeeRequestDTO.getDateTo());
      List<AttendanceDetailsForEmployeeDTO> attendanceDetailsForEmployeeDTOList = new ArrayList<>();
      List<Long> employeeIdList = new ArrayList<>();
      if (Objects.nonNull(attendanceDTOList) && !attendanceDTOList.isEmpty()) {
        attendanceDTOList.forEach(item -> {
          if (!employeeIdList.contains(item.getPayroll().getEmployee().getEmployeeId())) {
            AttendanceDetailsForEmployeeDTO attendanceDetailsForEmployeeDTO = new AttendanceDetailsForEmployeeDTO();
            attendanceDetailsForEmployeeDTO.setEmployeeId(item.getPayroll().getEmployee().getEmployeeId());
            attendanceDetailsForEmployeeDTO.setFullname(item.getPayroll().getEmployee().getFullname());
            List<AttendanceDTO> attendanceDTOList1 = attendanceDTOList.stream().filter(item1 ->
                    item1.getPayroll().getEmployee().getEmployeeId() == item.getPayroll().getEmployee().getEmployeeId()).collect(Collectors.toList());
            attendanceDetailsForEmployeeDTO.setAttendanceDTOList(attendanceDTOList1);
            attendanceDetailsForEmployeeDTOList.add(attendanceDetailsForEmployeeDTO);
              employeeIdList.add(item.getPayroll().getEmployee().getEmployeeId());
          }
        });
      }
      return attendanceDetailsForEmployeeDTOList;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
