package com.ags.spring_ecommerce_service.repository;

import com.ags.spring_ecommerce_service.entity.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  boolean existsBySku(String sku);
}
