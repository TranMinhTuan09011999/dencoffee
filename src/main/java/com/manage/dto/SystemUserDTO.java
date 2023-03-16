package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.UserViews;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemUserDTO extends AbstractDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private UserDTO user;

  @JsonView({UserViews.UserViewsSet.class})
  private RoleDTO role;

}
