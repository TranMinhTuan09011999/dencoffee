package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.UserViews;
import com.manage.model.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({UserViews.UserViewsSet.class})
  private Long userId;

  @JsonView({UserViews.UserViewsSet.class})
  private String username;

  @JsonView({UserViews.UserViewsSet.class})
  private String password;

  @JsonView({UserViews.UserViewsSet.class})
  private String fullname;

  @JsonView({UserViews.UserViewsSet.class})
  private Integer status;

  @JsonView({UserViews.UserViewsSet.class})
  private List<RoleDTO> roles;
}
