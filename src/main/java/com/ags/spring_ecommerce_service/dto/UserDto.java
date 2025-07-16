package com.ags.spring_ecommerce_service.dto;

import com.ags.spring_ecommerce_service.enums.UserRoleEnum;
import com.ags.spring_ecommerce_service.enums.UserStatusEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
  private UUID id;
  private String name;
  private String email;
  private String password;
  private String phone;
  private UserStatusEnum status;
  private UserRoleEnum role;
}
