package com.vaishnavishealthcare.modules.auth.service;

import java.util.List;

import com.vaishnavishealthcare.modules.auth.dto.CreateUserDTO;
import com.vaishnavishealthcare.modules.auth.dto.response.UserResponseDTO;
import com.vaishnavishealthcare.modules.auth.entity.User;

public interface UserService {
    UserResponseDTO createUser(CreateUserDTO user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}