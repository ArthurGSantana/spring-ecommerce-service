package com.ags.spring_ecommerce_service.entity;

import com.ags.spring_ecommerce_service.enums.OrderStatusEnum;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatusEnum status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderItem> items;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shipping_address_id", nullable = false)
  private Address shippingAddress;
}
