package com.ags.spring_ecommerce_service.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderHandler {

  @KafkaListener(topics = "${spring.kafka.topics.order-handler}", groupId = "order-handler-group")
  public void handleOrder(String message) {
    log.info("Received order message: {}", message);
    // Here you would typically process the order message, e.g., save it to the database
    // or perform some business logic.
  }
}
