package com.vaishnavishealthcare.modules.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vaishnavishealthcare.common.dto.ApiResponse;
import com.vaishnavishealthcare.modules.auth.dto.CreateUserDTO;
import com.vaishnavishealthcare.modules.auth.dto.request.UserRequestDTO;
import com.vaishnavishealthcare.modules.auth.dto.response.UserResponseDTO;
import com.vaishnavishealthcare.modules.auth.entity.Role;
import com.vaishnavishealthcare.modules.auth.entity.User;
import com.vaishnavishealthcare.modules.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        CreateUserDTO createUserDTO = new CreateUserDTO(
            userRequestDTO.username(),
            userRequestDTO.email(),
            userRequestDTO.password(),
            userRequestDTO.roles()
                .stream()
                .map(role -> new Role(role))
                .collect(Collectors.toSet())
        );
        UserResponseDTO result = userService.createUser(createUserDTO);
        return ApiResponse.created("User created successfully", result);
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> rsponseList = userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(rsponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.getUserById(id);
        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        user.setRoles(userRequestDTO.roles().stream().map(role -> new Role(role)).collect(Collectors.toSet()));
        userService.updateUser(id, user);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet())
        );
    }
}