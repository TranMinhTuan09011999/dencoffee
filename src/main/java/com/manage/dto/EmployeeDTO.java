package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.jsonview.EmployeeViews;
import com.manage.jsonview.UserViews;
import com.manage.util.DateHandler;
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
public class EmployeeDTO extends AbstractDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  private Long employeeId;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  private String fullname;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  @JsonSerialize(using = FormatDateSerializer.class)
  @JsonDeserialize(using = DateHandler.class)
  private Date birthday;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  private Integer gender;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  private String phoneNumber;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  private String address;

  @JsonView({EmployeeViews.EmployeeViewSet.class})
  private Integer status;
}
