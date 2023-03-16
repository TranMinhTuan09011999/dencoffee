package com.manage.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.jsonview.UserViews;
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
public class RoleDTO extends AbstractDTO implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({UserViews.UserViewsSet.class})
  private Long roleId;

  @JsonView({UserViews.UserViewsSet.class})
  private String name;

}
