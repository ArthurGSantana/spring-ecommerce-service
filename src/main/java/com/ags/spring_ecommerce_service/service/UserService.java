package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.UserDto;
import com.ags.spring_ecommerce_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;

  public void createUser(UserDto userDto) {
    log.info("Creating a new user");
  }
}
