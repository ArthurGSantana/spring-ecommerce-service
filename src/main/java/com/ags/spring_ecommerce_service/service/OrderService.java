package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.request.OrderRequestDto;
import com.ags.spring_ecommerce_service.entity.Order;
import com.ags.spring_ecommerce_service.entity.OrderItem;
import com.ags.spring_ecommerce_service.enums.OrderStatusEnum;
import com.ags.spring_ecommerce_service.exception.NotFoundException;
import com.ags.spring_ecommerce_service.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final AddressRepository addressRepository;
  private final ProductRepository productRepository;

  public void createOrder(OrderRequestDto orderRequestDto) {
    log.info("Creating order with details: {}", orderRequestDto);

    var user =
        userRepository
            .findById(orderRequestDto.getUserId())
            .orElseThrow(() -> new NotFoundException("User not found"));

    var address =
        addressRepository
            .findById(orderRequestDto.getShippingAddressId())
            .orElseThrow(() -> new NotFoundException("Address not found"));

    var order =
        Order.builder().user(user).shippingAddress(address).status(OrderStatusEnum.PENDING).build();

    try {
      var orderItems =
          orderRequestDto.getItems().stream()
              .map(
                  item ->
                      OrderItem.builder()
                          .order(order)
                          .product(productRepository.getReferenceById(item.getProductId()))
                          .quantity(item.getQuantity())
                          .build())
              .toList();

      order.setItems(orderItems);

      orderRepository.save(order);

      log.info("Order created successfully");
    } catch (Exception e) {
      log.error("Error creating order: {}", e.getMessage());
      throw new RuntimeException("Failed to create order", e);
    }
  }

  public void updateOrder(OrderRequestDto orderRequestDto) {
    log.info("Updating order with details: {}", orderRequestDto);

    var order =
        orderRepository
            .findById(orderRequestDto.getId())
            .orElseThrow(() -> new NotFoundException("Order not found"));

    if (order.getStatus() != OrderStatusEnum.PENDING) {
      log.warn(
          "Order with ID {} cannot be updated because it is not in PENDING status", order.getId());
      throw new RuntimeException("Order cannot be updated because it is not in PENDING status");
    }

    try {
      var orderItems =
          orderRequestDto.getItems().stream()
              .map(
                  item ->
                      OrderItem.builder()
                          .order(order)
                          .product(productRepository.getReferenceById(item.getProductId()))
                          .quantity(item.getQuantity())
                          .build())
              .toList();

      order.setItems(orderItems);

      orderRepository.save(order);

      log.info("Order updated successfully");
    } catch (Exception e) {
      log.error("Error updating order: {}", e.getMessage());
      throw new RuntimeException("Failed to update order", e);
    }
  }
}
