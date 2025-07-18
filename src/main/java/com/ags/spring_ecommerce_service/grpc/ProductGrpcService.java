package com.ags.spring_ecommerce_service.grpc;

import com.ags.spring_ecommerce_service.dto.ProductDto;
import com.ags.spring_ecommerce_service.enums.ProductStatusEnum;
import com.ags.spring_ecommerce_service.service.ProductService;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {
  private final ProductService productService;

  @Override
  public void createProduct(
      CreateProductRequest request, StreamObserver<CreateProductResponse> responseObserver) {
    try {
      var productDto =
          ProductDto.builder()
              .name(request.getName())
              .description(request.getDescription())
              .price(BigDecimal.valueOf(request.getPrice()))
              .stock(request.getStock())
              .status(ProductStatusEnum.AVAILABLE)
              .build();

      var product = productService.createProduct(productDto);

      var response =
          CreateProductResponse.newBuilder()
              .setId(product.getId().toString())
              .setSku(product.getSku())
              .setName(product.getName())
              .setDescription(product.getDescription())
              .setPrice(product.getPrice().doubleValue())
              .setStock(product.getStock())
              .setStatus(product.getStatus().name())
              .build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } catch (Exception e) {
      responseObserver.onError(
          Status.INTERNAL
              .withDescription("Create Product Error: " + e.getMessage())
              .asRuntimeException());
    }
  }

  @Override
  public void updateProduct(
      UpdateProductRequest request, StreamObserver<UpdateProductResponse> responseObserver) {
    try {
      var productDto =
          ProductDto.builder()
              .id(UUID.fromString(request.getId()))
              .name(request.getName())
              .description(request.getDescription())
              .price(BigDecimal.valueOf(request.getPrice()))
              .stock(request.getStock())
              .status(ProductStatusEnum.from(request.getStatus()))
              .build();

      var product = productService.updateProduct(productDto);

      var response =
          UpdateProductResponse.newBuilder()
              .setId(product.getId().toString())
              .setSku(product.getSku())
              .setName(product.getName())
              .setDescription(product.getDescription())
              .setPrice(product.getPrice().doubleValue())
              .setStock(product.getStock())
              .setStatus(product.getStatus().name())
              .build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } catch (Exception e) {
      responseObserver.onError(
          Status.INTERNAL
              .withDescription("Update Product Error: " + e.getMessage())
              .asRuntimeException());
    }
  }

  @Override
  public void deleteProduct(DeleteProductRequest request, StreamObserver<Empty> responseObserver) {
    try {
      productService.deleteProduct(UUID.fromString(request.getId()));

      responseObserver.onNext(Empty.getDefaultInstance());
      responseObserver.onCompleted();

    } catch (Exception e) {
      responseObserver.onError(
          Status.INTERNAL
              .withDescription("Delete Product Error: " + e.getMessage())
              .asRuntimeException());
    }
  }
}
