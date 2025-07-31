package com.ags.spring_ecommerce_service.dto.request;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
  private UUID userId;
  private UUID shippingAddressId;
  private List<OrderItemRequestDto> items;
}
