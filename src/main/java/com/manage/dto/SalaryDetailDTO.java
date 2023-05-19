package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.SalaryDetailViews;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDetailDTO extends AbstractNonAuditDTO implements java.io.Serializable {

  @JsonView({SalaryDetailViews.currentSalaryDetailViewsSet.class})
  private Long salaryDetailId;

  @JsonView({SalaryDetailViews.currentSalaryDetailViewsSet.class})
  private Double salary;

  @JsonView({SalaryDetailViews.currentSalaryDetailViewsSet.class})
  private Double allowance;

  private Date startDate;

  private Date endDate;

  @JsonView({SalaryDetailViews.currentSalaryDetailViewsSet.class})
  private PositionDTO position;

  private List<PayrollDTO> payrollList;

    public SalaryDetailDTO(Object[] object) {
    PositionDTO positionDTO = new PositionDTO();
    positionDTO.setPositionId(object[0] != null ? Long.valueOf(object[0].toString()) : null);
    positionDTO.setPositionName(object[1] != null ? object[1].toString() : null);
    this.position = positionDTO;
    this.salaryDetailId = object[2] != null ? Long.valueOf(object[2].toString()) : null;
    this.salary = object[3] != null ? Double.valueOf(object[3].toString()) : null;
    this.allowance = object[4] != null ? Double.valueOf(object[4].toString()) : null;
  }

}
