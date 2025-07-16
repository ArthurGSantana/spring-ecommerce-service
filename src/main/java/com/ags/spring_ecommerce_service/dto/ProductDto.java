package com.ags.spring_ecommerce_service.dto;

import com.ags.spring_ecommerce_service.enums.ProductStatusEnum;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
  private UUID id;
  private String sku;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
  private ProductStatusEnum status;
}
