package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.PositionDTO;
import com.manage.jsonview.PayrollViews;
import com.manage.jsonview.PositionViews;
import com.manage.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/position")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @GetMapping("/get-all-position")
  @JsonView({PositionViews.PositionViewsSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getPayrollForMonthYear() throws SystemException {
    List<PositionDTO> positionDTOList = positionService.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(positionDTOList);
  }

}
