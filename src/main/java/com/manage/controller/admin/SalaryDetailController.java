package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.SalaryDetailDTO;
import com.manage.dto.UpdatePayrollInforDTO;
import com.manage.jsonview.SalaryDetailViews;
import com.manage.services.bl.GetAllCurrentSalaryDetail;
import com.manage.services.bl.UpdateSalaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/salary-detail")
public class SalaryDetailController {

  @Autowired
  private GetAllCurrentSalaryDetail getAllCurrentSalaryDetail;

  @Autowired
  private UpdateSalaryDetailService updateSalaryDetailService;

  @GetMapping("/get-all-salary-detail")
  @JsonView({SalaryDetailViews.currentSalaryDetailViewsSet.class})
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllCurrentPayroll() throws SystemException {
    List<SalaryDetailDTO> result = getAllCurrentSalaryDetail.getAllCurrentSalaryDetail();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/update-salary-detail")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updatePayroll(@RequestBody UpdatePayrollInforDTO updatePayrollInforDTO) throws SystemException {
    boolean result = updateSalaryDetailService.updateSalaryDetail(updatePayrollInforDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
