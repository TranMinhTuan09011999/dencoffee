package com.manage.services.impl;

import com.manage.configuration.security.JwtTokenProvider;
import com.manage.configuration.security.dto.LoginRequest;
import com.manage.configuration.security.dto.LoginResponse;
import com.manage.configuration.security.dto.Token;
import com.manage.configuration.security.util.CookieUtil;
import com.manage.dto.UserDTO;
import com.manage.mapper.UserMapper;
import com.manage.mapper.helper.CycleAvoidingMappingContext;
import com.manage.model.Role;
import com.manage.model.User;
import com.manage.repository.UserRepository;
import com.manage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CookieUtil cookieUtil;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken) {
        String email = loginRequest.getUsername();
        User user = userRepository.findByUsername(email).orElseThrow(() -> new IllegalArgumentException("User not found with email " + email));

        Boolean accessTokenValid = tokenProvider.validateToken(accessToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        if (!accessTokenValid) {
            newAccessToken = tokenProvider.generateToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        LoginResponse loginResponse = new LoginResponse(LoginResponse.SuccessFailure.SUCCESS, "Auth successful. Tokens are created in cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        if (request.getCookies() != null) {
            System.out.println("i found some cookies");
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "logout successfully";
    }

    @Override
    public boolean hasAuthorize(String accessToken, String role) {
        Boolean refreshTokenValid = tokenProvider.validateToken(accessToken);
        if (!refreshTokenValid) {
            return false;
        }
        String username = tokenProvider.getUsername(accessToken);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with username " + username));
        if (user != null) {
            List<Role> roleList = user.getRoles();
            if (roleList != null && roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    if (roleList.get(i).getName().equals(role)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public UserDTO findUserByUserName(String userName) {
        User user = userRepository.getUserByUsername(userName);
        return userMapper.toDto(user, new CycleAvoidingMappingContext());
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }
}
