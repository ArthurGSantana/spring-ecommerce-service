package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.UserDto;
import com.ags.spring_ecommerce_service.entity.User;
import com.ags.spring_ecommerce_service.exception.NotFoundException;
import com.ags.spring_ecommerce_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ObjectMapper objectMapper;

  private static final String USER_NOT_FOUND_MESSAGE = "User not found";

  public UserDto createUser(UserDto userDto) {
    log.info("Creating a new user");

    existsByEmail(userDto.getEmail());

    var user = objectMapper.convertValue(userDto, User.class);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    user = userRepository.save(user);

    log.info("User with email {} created successfully", userDto.getEmail());

    userDto.setId(user.getId());
    userDto.setPassword(null);

    return userDto;
  }

  public UserDto updateUser(UUID id, UserDto userDto) {
    log.info("Updating user with ID {}", id);

    var user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

    if (!user.getEmail().equals(userDto.getEmail())) existsByEmail(userDto.getEmail());

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPhone(userDto.getPhone());
    user.setStatus(userDto.getStatus());

    user = userRepository.save(user);

    log.info("User with ID {} updated successfully", id);

    var updatedUserDto = objectMapper.convertValue(user, UserDto.class);
    updatedUserDto.setPassword(null);

    return updatedUserDto;
  }

  public UserDto getUserById(UUID id) {
    log.info("Fetching user with ID {}", id);

    var user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

    var userDto = objectMapper.convertValue(user, UserDto.class);
    userDto.setPassword(null);

    log.info("User with ID {} fetched successfully", id);

    return userDto;
  }

  public void deleteUserById(UUID id) {
    log.info("Deleting user with ID {}", id);

    var user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

    userRepository.delete(user);

    log.info("User with ID {} deleted successfully", id);
  }

  private void existsByEmail(String email) {
    var exists = userRepository.existsByEmail(email);

    if (exists) {
      log.error("User with email {} already exists", email);
      throw new IllegalArgumentException("User with this email already exists");
    }
  }
}
