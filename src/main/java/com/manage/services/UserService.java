package com.manage.services;

import com.manage.dto.UserDTO;

public interface UserService {
  UserDTO findUserByUserName(String userName);
}
