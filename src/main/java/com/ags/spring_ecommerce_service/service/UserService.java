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

  public UserDto createUser(UserDto userDto) {
    log.info("Creating a new user");

    var exists = userRepository.existsByEmail(userDto.getEmail());

    if (exists) {
      log.error("User with email {} already exists", userDto.getEmail());
      throw new IllegalArgumentException("User with this email already exists");
    }

    var user = objectMapper.convertValue(userDto, User.class);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    user = userRepository.save(user);

    log.info("User with email {} created successfully", userDto.getEmail());

    userDto.setId(user.getId());
    userDto.setPassword(null);

    return userDto;
  }

  public UserDto getUserById(UUID id) {
    log.info("Fetching user with ID {}", id);

    var user =
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

    var userDto = objectMapper.convertValue(user, UserDto.class);
    userDto.setPassword(null); // Do not expose password

    log.info("User with ID {} fetched successfully", id);

    return userDto;
  }
}
