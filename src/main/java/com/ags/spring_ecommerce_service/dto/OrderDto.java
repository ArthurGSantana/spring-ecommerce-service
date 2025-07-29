package com.ags.spring_ecommerce_service.dto;

import com.ags.spring_ecommerce_service.enums.OrderStatusEnum;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
  private UUID id;
  private OrderStatusEnum status;
  private UUID userId;
  private UUID shippingAddressId;
  private List<OrderItemDto> items;
}
