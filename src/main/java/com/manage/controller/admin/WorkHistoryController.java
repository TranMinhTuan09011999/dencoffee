package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.WorkHistoryDTO;
import com.manage.jsonview.WorkHistoryViews;
import com.manage.services.WorkHistoryService;
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
@RequestMapping("/api/admin/work-history")
public class WorkHistoryController {

  @Autowired
  private WorkHistoryService workHistoryService;

  @GetMapping("/{employeeId}")
  @JsonView({WorkHistoryViews.WorkHistoryViewSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getWorkHistoryByEmployeeId(@PathVariable(value = "employeeId") Long employeeId) throws SystemException {
    List<WorkHistoryDTO> workHistoryDTOList = workHistoryService.getWorkHistoryByEmployeeId(employeeId);
    return ResponseEntity.status(HttpStatus.OK).body(workHistoryDTOList);
  }
}
