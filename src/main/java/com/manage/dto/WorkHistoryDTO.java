package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.jsonview.WorkHistoryViews;
import com.manage.util.FormatDateSerializer;
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
public class WorkHistoryDTO extends AbstractDTO implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @JsonView({WorkHistoryViews.WorkHistoryViewSet.class})
  private Long workHistoryId;

  @JsonView({WorkHistoryViews.WorkHistoryViewSet.class})
  @JsonSerialize(using = FormatDateSerializer.class)
  private Date startDate;

  @JsonView({WorkHistoryViews.WorkHistoryViewSet.class})
  @JsonSerialize(using = FormatDateSerializer.class)
  private Date endDate;

  private EmployeeDTO employeeDTO;
}
