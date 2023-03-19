package com.manage.configurations.security;

import com.manage.dto.UserDTO;
import com.manage.model.User;
import com.manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder bcryptEncoder;

  @Override
  public MyUserDetails loadUserByUsername(String username)
          throws UsernameNotFoundException {
    User user = userRepository.getUserByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Could not find user");
    }

    return new MyUserDetails(user);
  }

  public User save() {
    User newUser = new User();
    newUser.setUsername("minhtuan123");
    newUser.setFullname("Minh Tuan");
    newUser.setPassword(bcryptEncoder.encode("minhtuan123"));
    newUser.setStatus(1);
    newUser.setCreatedBy("Minhtuan");
    newUser.setCreatedDate(new Date());
    newUser.setModifiedBy("Minhtuan");
    newUser.setModifiedDate(new Date());
    return userRepository.save(newUser);
  }

}
