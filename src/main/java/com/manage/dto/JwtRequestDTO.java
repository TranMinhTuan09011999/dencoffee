package com.manage.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequestDTO implements Serializable {
  private String username;
  private String password;
}
