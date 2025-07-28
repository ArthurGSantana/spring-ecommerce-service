package com.ags.spring_ecommerce_service.repository;

import com.ags.spring_ecommerce_service.entity.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {}
