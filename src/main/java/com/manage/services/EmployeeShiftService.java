package com.manage.services;

import com.manage.dto.EmployeeShiftDTO;
import com.manage.model.EmployeeShift;

import java.util.List;

public interface EmployeeShiftService {

    List<EmployeeShiftDTO> findAllEmployeeShift();

    EmployeeShift getEmployeeShiftById(Long employeeShiftId);

}
