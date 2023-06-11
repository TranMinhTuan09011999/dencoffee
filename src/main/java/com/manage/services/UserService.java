package com.manage.services;

import com.manage.configuration.security.dto.LoginRequest;
import com.manage.configuration.security.dto.LoginResponse;
import com.manage.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken);

    String logout(HttpServletRequest request, HttpServletResponse response);

    boolean hasAuthorize(String accessToken, String role);

    UserDTO findUserByUserName(String userName);
}
