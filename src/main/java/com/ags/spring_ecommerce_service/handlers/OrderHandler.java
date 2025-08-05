package com.ags.spring_ecommerce_service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderHandler {
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.order-handler}", groupId = "order-handler-group")
  public void handleOrder(String message) throws JsonProcessingException {
    EventData<OrderData> eventData = objectMapper.readValue(message, new TypeReference<>() {});

    OrderData order = eventData.getBefore();
    OrderData after = eventData.getAfter();

    log.info("Received order message from Debezium: {}", message);
  }
}
