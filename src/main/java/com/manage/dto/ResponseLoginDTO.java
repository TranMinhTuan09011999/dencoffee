package com.manage.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseLoginDTO implements java.io.Serializable  {

  private static final long serialVersionUID = 1L;

  private Long userId;
  private String username;
  private String fullname;
  private String token;
  private List<String> roles;
}
