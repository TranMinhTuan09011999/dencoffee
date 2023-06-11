package com.manage.controller.admin;

import com.manage.configuration.security.dto.ApiResponseMessage;
import com.manage.configuration.security.dto.LoginRequest;
import com.manage.configuration.security.dto.LoginResponse;
import com.manage.configuration.security.util.SecurityCipher;
import com.manage.model.User;
import com.manage.repository.UserRepository;
import com.manage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> createAuthenticationToken(@CookieValue(name = "accessToken", required = false) String accessToken,
                                                                   @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
            return userService.login(loginRequest, decryptedAccessToken);
        } catch (Exception e) {
            logger.error("Error", e);
            throw new SystemException();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logOut(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity(new ApiResponseMessage(true, userService.logout(request, response)), HttpStatus.OK);
    }

    @GetMapping(value = "/hasAuthorize/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasAuthorize(@CookieValue(name = "accessToken", required = false) String accessToken,
                                                @PathVariable(value = "role") String role) {
        String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
        boolean result = userService.hasAuthorize(decryptedAccessToken, role);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/register")
    public ResponseEntity<?> register() throws Exception {
        User newUser = new User();
        newUser.setUsername("user");
        newUser.setFullname("user");
        newUser.setPassword(bcryptEncoder.encode("user"));
        newUser.setStatus(1);
        newUser.setCreatedBy("user");
        newUser.setCreatedDate(new Date());
        newUser.setModifiedBy("user");
        newUser.setModifiedDate(new Date());
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
