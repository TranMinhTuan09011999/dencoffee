package com.manage.controller;

import com.manage.configurations.security.JwtTokenUtil;
import com.manage.configurations.security.JwtUserDetailsService;
import com.manage.configurations.security.MyUserDetails;
import com.manage.dto.ResponseLoginDTO;
import com.manage.dto.JwtRequestDTO;
import com.manage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @PostMapping(value = "/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {
    try {
      authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
      final MyUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
      final String token = jwtTokenUtil.generateToken(userDetails);
      ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
      responseLoginDTO.setUsername(userDetails.getUser().getUsername());
      responseLoginDTO.setUserId(userDetails.getUser().getUserId());
      responseLoginDTO.setFullname(userDetails.getUser().getFullname());
      responseLoginDTO.setToken(token);
      responseLoginDTO.setRoles(userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()));
      return ResponseEntity.ok(responseLoginDTO);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  @GetMapping(value = "/register")
  public ResponseEntity<?> order() throws Exception {
    User user = jwtUserDetailsService.save();
    return ResponseEntity.ok(user);
  }


  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
