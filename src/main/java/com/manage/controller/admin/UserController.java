package com.manage.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.manage.dto.UserDTO;
import com.manage.jsonview.UserViews;
import com.manage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping("/{userName}")
  @JsonView({UserViews.UserViewsSet.class})
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getUser(@PathVariable(value = "userName") String userName) throws SystemException {
    try {
      UserDTO user = userService.findUserByUserName(userName);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }
}
