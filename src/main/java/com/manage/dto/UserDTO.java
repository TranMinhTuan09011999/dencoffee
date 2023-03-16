package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.UserViews;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({UserViews.UserViewsSet.class})
  private Long id;

  @JsonView({UserViews.UserViewsSet.class})
  private String username;

  @JsonView({UserViews.UserViewsSet.class})
  private String password;

  @JsonView({UserViews.UserViewsSet.class})
  private String fullname;

  @JsonView({UserViews.UserViewsSet.class})
  private String address;

  @JsonView({UserViews.UserViewsSet.class})
  private String phone_number;

  @JsonView({UserViews.UserViewsSet.class})
  private Date birthday;

  @JsonView({UserViews.UserViewsSet.class})
  private Integer status;

  @JsonView({UserViews.UserViewsSet.class})
  private List<SystemUserDTO> systemUsers;
}
