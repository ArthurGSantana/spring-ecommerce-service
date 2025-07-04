package com.ags.spring_ecommerce_service.controller;

import com.ags.spring_ecommerce_service.dto.UserDto;
import com.ags.spring_ecommerce_service.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("{id}")
  @Tag(name = "User", description = "Get user by ID")
  public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
    var user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  @Tag(name = "User", description = "Create a new user")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    var createdUser = userService.createUser(userDto);
    return ResponseEntity.ok(createdUser);
  }
}
