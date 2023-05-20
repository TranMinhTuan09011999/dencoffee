package com.manage.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class IpAddressDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private Long ipAddressId;

  private String ipAddress;

  private String location;

}
