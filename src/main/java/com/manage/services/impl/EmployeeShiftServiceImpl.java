package com.manage.services.impl;

import com.manage.dto.EmployeeShiftDTO;
import com.manage.mapper.EmployeeShiftMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.EmployeeShift;
import com.manage.repository.EmployeeShiftRepository;
import com.manage.services.EmployeeShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeShiftServiceImpl implements EmployeeShiftService {

    @Autowired
    private EmployeeShiftRepository employeeShiftRepository;

    @Autowired
    private EmployeeShiftMapper employeeShiftMapper;

    @Override
    public List<EmployeeShiftDTO> findAllEmployeeShift() {
        List<EmployeeShift> employeeShiftList = employeeShiftRepository.findAllOrderDESC();
        if (!CollectionUtils.isEmpty(employeeShiftList)) {
            return employeeShiftList.stream().map(e -> employeeShiftMapper
                    .toDto(e, new CycleAvoidingMappingContext())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public EmployeeShift getEmployeeShiftById(Long employeeShiftId) {
        return employeeShiftRepository.getOne(employeeShiftId);
    }

}
