package com.manage.dto;

import lombok.Data;

@Data
public class ResponseLoginDTO implements java.io.Serializable  {

  private static final long serialVersionUID = 1L;

  private Long userId;
  private String username;
  private String fullname;
}
