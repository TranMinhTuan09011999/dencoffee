package com.manage.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.EmployeeDTO;
import com.manage.jsonview.EmployeeViews;
import com.manage.services.bl.GetEmployeeNameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/user/employee")
public class EmployeeForUserController {

    @Autowired
    private GetEmployeeNameListService getEmployeeNameListService;

    @GetMapping("/employee-name/{status}")
    @PreAuthorize("hasRole('USER')")
    @JsonView({EmployeeViews.EmployeeNameViewSet.class})
    public ResponseEntity<?> getAllEmployeeName(@PathVariable(value = "status") Integer status) throws SystemException {
        List<EmployeeDTO> customerDTOList = getEmployeeNameListService.getEmployeeNameList(status);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOList);
    }

}
