package com.ags.spring_ecommerce_service.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
  private UUID id;
  private String street;
  private String number;
  private String city;
  private String state;
  private String zipCode;
  private UUID userId;
}
