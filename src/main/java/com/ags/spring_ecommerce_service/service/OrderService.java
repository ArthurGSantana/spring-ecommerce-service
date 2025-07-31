package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.request.OrderRequestDto;
import com.ags.spring_ecommerce_service.entity.Order;
import com.ags.spring_ecommerce_service.entity.OrderItem;
import com.ags.spring_ecommerce_service.enums.OrderStatusEnum;
import com.ags.spring_ecommerce_service.exception.NotFoundException;
import com.ags.spring_ecommerce_service.repository.AddressRepository;
import com.ags.spring_ecommerce_service.repository.OrderRepository;
import com.ags.spring_ecommerce_service.repository.ProductRepository;
import com.ags.spring_ecommerce_service.repository.UserRepository;
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
  }
}
