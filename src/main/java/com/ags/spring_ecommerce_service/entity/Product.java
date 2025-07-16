package com.ags.spring_ecommerce_service.entity;

import com.ags.spring_ecommerce_service.enums.ProductStatusEnum;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "sku", nullable = false, unique = true)
  private String sku;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductStatusEnum status;
}
