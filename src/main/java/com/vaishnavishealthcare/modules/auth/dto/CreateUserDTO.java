package com.vaishnavishealthcare.modules.auth.dto;

import java.util.Set;

import com.vaishnavishealthcare.modules.auth.entity.Role;

public record CreateUserDTO(
    String username,
    String email,
    String password,
    Set<Role> roles
) {

    public void validate() {
        if (this.username == null || this.username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (this.email == null || this.email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (this.password == null || this.password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (this.roles == null || this.roles.isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be empty");
        }
    }
}