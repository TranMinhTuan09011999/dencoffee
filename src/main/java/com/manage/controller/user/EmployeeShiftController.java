package com.manage.controller.user;

import com.manage.dto.EmployeeShiftDTO;
import com.manage.services.bl.GetEmployeeShiftByCurrentTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/employee-shift")
public class EmployeeShiftController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeShiftController.class);

    @Autowired
    private GetEmployeeShiftByCurrentTimeService getEmployeeShiftByCurrentTime;

    @GetMapping("/current-time")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getEmployeeShiftByCurrentTime() throws Exception {
        try {

            EmployeeShiftDTO employeeShiftDTO = getEmployeeShiftByCurrentTime.getEmployeeShiftByCurrentTime();
            logger.debug("GetEmployeeShiftByCurrentTime", employeeShiftDTO);
            return ResponseEntity.status(HttpStatus.OK).body(employeeShiftDTO);
        } catch (Exception e) {
            logger.error("Error", e);
            throw new Exception();
        }
    }

}
