package com.vaishnavishealthcare.modules.auth.service.impl;

import com.vaishnavishealthcare.modules.auth.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.vaishnavishealthcare.modules.auth.repository.UserRepository;
import com.vaishnavishealthcare.common.exception.BadRequestException;
import com.vaishnavishealthcare.modules.auth.dto.CreateUserDTO;
import com.vaishnavishealthcare.modules.auth.dto.response.UserResponseDTO;
import com.vaishnavishealthcare.modules.auth.entity.User;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private UserResponseDTO toResponseDTO(User user) {
        
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet())
        );
    }

    @Override
    public UserResponseDTO createUser(CreateUserDTO user) {
        user.validate();

        if (userRepository.existsByEmail(user.email())) {
            throw new BadRequestException("Email already exists");
        }

        User newUser = new User();
        newUser.setUsername(user.username());    
        newUser.setEmail(user.email());
        newUser.setPassword(user.password());
        newUser.setRoles(user.roles());

        User savedUser = userRepository.save(newUser);
        return toResponseDTO(savedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User does not exist");
        }
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User does not exist");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User does not exist");
        }
        userRepository.deleteById(id);
    }
}
