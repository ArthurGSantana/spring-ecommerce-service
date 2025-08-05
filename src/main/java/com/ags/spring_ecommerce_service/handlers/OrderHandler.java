package com.ags.spring_ecommerce_service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderHandler {
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.order-handler}", groupId = "order-handler-group")
  public void handleOrder(@Nullable String message, @Header("kafka_receivedMessageKey") String keyJson) throws JsonProcessingException {
    EventData<OrderData> eventData = objectMapper.readValue(message, new TypeReference<>() {});

    log.info("Received key from Kafka: {}", keyJson);

    switch (eventData.getOp()) {
      case "c" -> log.info("Operation is create");
      case "u" -> log.info("Operation is update");
      case "d" -> log.info("Operation is delete");
      default -> log.warn("Unknown operation: {}", eventData.getOp());
    }

    OrderData order = eventData.getBefore();
    OrderData after = eventData.getAfter();

    log.info("Received order message from Debezium: {}", message);
  }
}
