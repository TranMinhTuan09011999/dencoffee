package com.manage.dto;

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

  private Long employeeId;

  private String fullname;

  private Date birthday;

  private Integer gender;

  private String phoneNumber;

  private String address;

  private Integer status;
}
