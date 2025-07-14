package com.ags.spring_ecommerce_service.entity;

import com.ags.spring_ecommerce_service.enums.ProductStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductStatusEnum status;
}
