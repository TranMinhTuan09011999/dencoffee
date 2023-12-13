package com.manage.services.bl;

import com.manage.dto.EmployeeShiftDTO;
import com.manage.services.EmployeeShiftService;
import com.manage.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class GetEmployeeShiftByCurrentTimeService {

    private static final Logger logger = LoggerFactory.getLogger(GetEmployeeShiftByCurrentTimeService.class);

    @Autowired
    private EmployeeShiftService employeeShiftService;

    public EmployeeShiftDTO getEmployeeShiftByCurrentTime() throws Exception {
        try {
            LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            List<EmployeeShiftDTO> employeeShiftDTOList = employeeShiftService.findAllEmployeeShift();
            if (!CollectionUtils.isEmpty(employeeShiftDTOList)) {
                for (int i = 0; i < employeeShiftDTOList.size(); i++) {
                    EmployeeShiftDTO employeeShiftDTO = employeeShiftDTOList.get(i);
                    String startTimeStr = employeeShiftDTO.getStartTime();
                    String endTimeStr = employeeShiftDTO.getEndTime();
                    LocalTime startTime = DateUtils.convertStringToLocalTime(startTimeStr);
                    LocalTime endTime = DateUtils.convertStringToLocalTime(endTimeStr);
                    if (currentTime.compareTo(startTime) < 0) {
                        currentTime = currentTime.plusMinutes(30);
                    }
                    boolean check = DateUtils.checkBetween2LocalTime(startTime, endTime, currentTime);
                    if (check) {
                        return employeeShiftDTO;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("GetEmployeeShiftByCurrentTimeService Error", e);
            throw new Exception();
        }
    }

}
