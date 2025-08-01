package com.ags.spring_ecommerce_service.dto.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDto {
  private UUID id;
  private UUID productId;
  private Integer quantity;
}
