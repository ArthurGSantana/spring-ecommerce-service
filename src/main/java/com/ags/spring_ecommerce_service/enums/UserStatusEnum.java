package com.ags.spring_ecommerce_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatusEnum {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE"),
  BLOCKED("BLOCKED");

  private final String status;
}
