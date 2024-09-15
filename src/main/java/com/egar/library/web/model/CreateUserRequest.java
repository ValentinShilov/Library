package com.egar.library.web.model;

import com.egar.library.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String username;
    private String email;
    private Set<RoleType> roles;
    private String password;
}
