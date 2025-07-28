package com.ags.spring_ecommerce_service.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderItem extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  private int quantity;
}
