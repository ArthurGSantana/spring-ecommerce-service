package com.ags.spring_ecommerce_service.entity;

import com.ags.spring_ecommerce_service.enums.UserRoleEnum;
import com.ags.spring_ecommerce_service.enums.UserStatusEnum;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone", length = 20)
  private String phone;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserStatusEnum status;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;
}
