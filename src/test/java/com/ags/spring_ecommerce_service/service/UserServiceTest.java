package com.ags.spring_ecommerce_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.ags.spring_ecommerce_service.dto.UserDto;
import com.ags.spring_ecommerce_service.entity.User;
import com.ags.spring_ecommerce_service.exception.NotFoundException;
import com.ags.spring_ecommerce_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private ObjectMapper objectMapper;

  @InjectMocks private UserService userService;

  private UserDto userDto;
  private User user;
  private UUID userId;

  @BeforeEach
  void setUp() {
    userId = UUID.randomUUID();

    userDto =
        UserDto.builder()
            .name("João Silva")
            .email("joao@email.com")
            .phone("11999999999")
            .password("senha123")
            .build();

    user =
        User.builder()
            .id(userId)
            .name("João Silva")
            .email("joao@email.com")
            .phone("11999999999")
            .password("encodedPassword")
            .build();
  }

  @Nested
  @DisplayName("Create User Tests")
  class CreateUserTests {

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
      // Given
      when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
      when(objectMapper.convertValue(userDto, User.class)).thenReturn(user);
      when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
      when(userRepository.save(any(User.class))).thenReturn(user);

      // When
      UserDto result = userService.createUser(userDto);

      // Then
      assertNotNull(result);
      assertEquals(userId, result.getId());
      assertNull(result.getPassword());

      verify(userRepository).existsByEmail(userDto.getEmail());
      verify(objectMapper).convertValue(userDto, User.class);
      verify(passwordEncoder).encode("senha123");
      verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
      // Given
      when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

      // When & Then
      IllegalArgumentException exception =
          assertThrows(IllegalArgumentException.class, () -> userService.createUser(userDto));

      assertEquals("User with this email already exists", exception.getMessage());
      verify(userRepository).existsByEmail(userDto.getEmail());
      verify(userRepository, never()).save(any());
    }
  }

  @Nested
  @DisplayName("Update User Tests")
  class UpdateUserTests {

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
      // Given
      UserDto updateDto =
          UserDto.builder()
              .name("João Santos")
              .email("joao@email.com")
              .phone("11888888888")
              .build();

      User updatedUser =
          User.builder()
              .id(userId)
              .name("João Santos")
              .email("joao@email.com")
              .phone("11888888888")
              .password("encodedPassword")
              .build();

      when(userRepository.findById(userId)).thenReturn(Optional.of(user));
      when(userRepository.save(any(User.class))).thenReturn(updatedUser);
      when(objectMapper.convertValue(updatedUser, UserDto.class)).thenReturn(updateDto);

      // When
      UserDto result = userService.updateUser(userId, updateDto);

      // Then
      assertNotNull(result);
      assertEquals("João Santos", result.getName());
      assertEquals("11888888888", result.getPhone());
      assertNull(result.getPassword());

      verify(userRepository).findById(userId);
      verify(userRepository).save(any(User.class));
      verify(objectMapper).convertValue(updatedUser, UserDto.class);
    }

    @Test
    @DisplayName("Should update user with different email successfully")
    void shouldUpdateUserWithDifferentEmailSuccessfully() {
      // Given
      UserDto updateDto =
          UserDto.builder()
              .name("João Santos")
              .email("joao.santos@email.com")
              .phone("11888888888")
              .build();

      when(userRepository.findById(userId)).thenReturn(Optional.of(user));
      when(userRepository.existsByEmail(updateDto.getEmail())).thenReturn(false);
      when(userRepository.save(any(User.class))).thenReturn(user);
      when(objectMapper.convertValue(any(User.class), eq(UserDto.class))).thenReturn(updateDto);

      // When
      UserDto result = userService.updateUser(userId, updateDto);

      // Then
      assertNotNull(result);
      verify(userRepository).existsByEmail(updateDto.getEmail());
      verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found for update")
    void shouldThrowExceptionWhenUserNotFoundForUpdate() {
      // Given
      when(userRepository.findById(userId)).thenReturn(Optional.empty());

      // When & Then
      NotFoundException exception =
          assertThrows(NotFoundException.class, () -> userService.updateUser(userId, userDto));

      assertEquals("User not found", exception.getMessage());
      verify(userRepository).findById(userId);
      verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when new email already exists")
    void shouldThrowExceptionWhenNewEmailAlreadyExists() {
      // Given
      UserDto updateDto =
          UserDto.builder()
              .name("João Santos")
              .email("outro@email.com")
              .phone("11888888888")
              .build();

      when(userRepository.findById(userId)).thenReturn(Optional.of(user));
      when(userRepository.existsByEmail(updateDto.getEmail())).thenReturn(true);

      // When & Then
      IllegalArgumentException exception =
          assertThrows(
              IllegalArgumentException.class, () -> userService.updateUser(userId, updateDto));

      assertEquals("User with this email already exists", exception.getMessage());
      verify(userRepository).existsByEmail(updateDto.getEmail());
      verify(userRepository, never()).save(any());
    }
  }

  @Nested
  @DisplayName("Get User Tests")
  class GetUserTests {

    @Test
    @DisplayName("Should get user by ID successfully")
    void shouldGetUserByIdSuccessfully() {
      // Given
      when(userRepository.findById(userId)).thenReturn(Optional.of(user));
      when(objectMapper.convertValue(user, UserDto.class)).thenReturn(userDto);

      // When
      UserDto result = userService.getUserById(userId);

      // Then
      assertNotNull(result);
      assertNull(result.getPassword());

      verify(userRepository).findById(userId);
      verify(objectMapper).convertValue(user, UserDto.class);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
      // Given
      when(userRepository.findById(userId)).thenReturn(Optional.empty());

      // When & Then
      NotFoundException exception =
          assertThrows(NotFoundException.class, () -> userService.getUserById(userId));

      assertEquals("User not found", exception.getMessage());
      verify(userRepository).findById(userId);
    }
  }

  @Nested
  @DisplayName("Delete User Tests")
  class DeleteUserTests {

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
      // Given
      when(userRepository.findById(userId)).thenReturn(Optional.of(user));

      // When
      assertDoesNotThrow(() -> userService.deleteUserById(userId));

      // Then
      verify(userRepository).findById(userId);
      verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Should throw exception when user not found for deletion")
    void shouldThrowExceptionWhenUserNotFoundForDeletion() {
      // Given
      when(userRepository.findById(userId)).thenReturn(Optional.empty());

      // When & Then
      NotFoundException exception =
          assertThrows(NotFoundException.class, () -> userService.deleteUserById(userId));

      assertEquals("User not found", exception.getMessage());
      verify(userRepository).findById(userId);
      verify(userRepository, never()).delete(any());
    }
  }
}
