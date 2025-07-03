package com.ags.spring_ecommerce_service.dto;

import com.ags.spring_ecommerce_service.enums.UserRoleEnum;
import com.ags.spring_ecommerce_service.enums.UserStatusEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private UUID id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
  private String phone;

  @NotNull(message = "Status is required")
  private UserStatusEnum status;

  @NotNull(message = "Role is required")
  private UserRoleEnum role;
}
