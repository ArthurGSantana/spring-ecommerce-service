package com.ags.spring_ecommerce_service.repository;

import com.ags.spring_ecommerce_service.entity.OrderItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {}
