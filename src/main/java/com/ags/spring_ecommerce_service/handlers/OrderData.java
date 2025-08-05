package com.ags.spring_ecommerce_service.handlers;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class OrderData {
  private UUID id;
  private String status;
  private UUID userId;
  private UUID shippingAddressId;
}
