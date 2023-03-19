package com.manage.services.impl;

import com.manage.dto.UserDTO;
import com.manage.mapper.UserMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.User;
import com.manage.repository.UserRepository;
import com.manage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDTO findUserByUserName(String userName) {
    User user = userRepository.getUserByUsername(userName);
    return userMapper.toDto(user, new CycleAvoidingMappingContext());
  }
}
