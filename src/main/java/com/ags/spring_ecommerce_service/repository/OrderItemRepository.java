package com.ags.spring_ecommerce_service.repository;

import com.ags.spring_ecommerce_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {}
