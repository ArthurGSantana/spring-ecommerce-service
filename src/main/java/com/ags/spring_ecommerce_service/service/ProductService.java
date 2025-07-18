package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.ProductDto;
import com.ags.spring_ecommerce_service.entity.Product;
import com.ags.spring_ecommerce_service.exception.NotFoundException;
import com.ags.spring_ecommerce_service.repository.ProductRepository;
import com.ags.spring_ecommerce_service.utils.SkuGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
  private final ObjectMapper objectMapper;

  private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

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

  public ProductDto updateProduct(ProductDto productDto) {
    log.info("Updating product with sku {}", productDto.getSku());

    var product =
        productRepository
            .findById(productDto.getId())
            .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_MESSAGE));

    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    product.setPrice(productDto.getPrice());
    product.setStock(productDto.getStock());

    product = productRepository.save(product);

    log.info("Product with sku {} updated successfully", product.getSku());

    return objectMapper.convertValue(product, ProductDto.class);
  }

  public void deleteProduct(UUID id) {
    log.info("Deleting product with id {}", id);

    var product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_MESSAGE));

    productRepository.delete(product);

    log.info("Product with id {} deleted successfully", id);
  }
}
