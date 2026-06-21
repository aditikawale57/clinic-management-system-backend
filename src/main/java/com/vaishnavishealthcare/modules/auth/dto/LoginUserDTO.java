package com.vaishnavishealthcare.modules.auth.dto;

public record LoginUserDTO(
    String username,
    String password
) {
    public void validate() {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

}
