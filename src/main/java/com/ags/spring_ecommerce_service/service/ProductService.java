package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.ProductDto;
import com.ags.spring_ecommerce_service.entity.Product;
import com.ags.spring_ecommerce_service.repository.ProductRepository;
import com.ags.spring_ecommerce_service.utils.SkuGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
  private final ObjectMapper objectMapper;

  public ProductDto createProduct(ProductDto productDto) {
    log.info("Creating a new product");

    productDto.setSku(SkuGenerator.generate(productDto.getName()));

    existsBySku(productDto.getSku());

    Product product = objectMapper.convertValue(productDto, Product.class);

    product = productRepository.save(product);

    log.info("Product with sku {} created successfully", product.getSku());

    return objectMapper.convertValue(product, ProductDto.class);
  }

  private void existsBySku(String sku) {
    var exists = productRepository.existsBySku(sku);

    if (exists) {
      log.error("Product with sku {} already exists", sku);
      throw new IllegalArgumentException("Product with this sku already exists");
    }
  }
}
