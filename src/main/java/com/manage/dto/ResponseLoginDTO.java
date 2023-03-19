package com.manage.dto;

import lombok.Data;

@Data
public class ResponseLoginDTO {

  private String jwttoken;
  private Long userId;
  private String username;
  private String fullname;

}
