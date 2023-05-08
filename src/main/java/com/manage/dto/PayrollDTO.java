package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.PayrollViews;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDTO extends AbstractNonAuditDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({PayrollViews.PayrollViewForCurrentSet.class})
  private Long payrollId;

  @JsonView({PayrollViews.PayrollViewForCurrentSet.class})
  private Double salary;

  @JsonView({PayrollViews.PayrollViewForCurrentSet.class})
  private Double allowance;

  @JsonView({PayrollViews.PayrollViewForCurrentSet.class})
  private Double bonus;

  private Date startDate;

  private Date endDate;

  @JsonView({PayrollViews.PayrollViewForCurrentSet.class})
  private PositionDTO position;

  public PayrollDTO(Object[] object) {
    PositionDTO positionDTO = new PositionDTO();
    positionDTO.setPositionId(object[0] != null ? Long.valueOf(object[0].toString()) : null);
    positionDTO.setPositionName(object[1] != null ? object[1].toString() : null);
    this.position = positionDTO;
    this.payrollId = object[2] != null ? Long.valueOf(object[2].toString()) : null;
    this.salary = object[3] != null ? Double.valueOf(object[3].toString()) : null;
    this.allowance = object[4] != null ? Double.valueOf(object[4].toString()) : null;
    this.bonus = object[5] != null ? Double.valueOf(object[5].toString()) : null;
  }

}
