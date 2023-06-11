package com.manage.configuration.security.dto;

import com.manage.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfile {
    private Long id;
    private String username;

    List<Role> roles = new ArrayList<>();
}
