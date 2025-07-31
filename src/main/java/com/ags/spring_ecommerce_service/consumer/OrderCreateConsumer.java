package com.ags.spring_ecommerce_service.consumer;

import com.ags.spring_ecommerce_service.dto.request.OrderRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreateConsumer {
  private final ObjectMapper objectMapper;

  @RetryableTopic(
      attempts = "3", // 1 tentativa inicial + 2 retries
      backoff = @Backoff(delay = 2000),
      dltTopicSuffix = ".DLT" // Define o sufixo do tópico DLQ
      )
  @KafkaListener(topics = "${spring.kafka.topics.order-create}", groupId = "order-create-group")
  public void consume(@Nullable String message) {
    if (message == null || message.isBlank()) {
      log.warn("Received empty or null message, skipping processing.");
      return;
    }

    try {
      OrderRequestDto orderRequestDto = objectMapper.readValue(message, OrderRequestDto.class);
      log.info("Message successfully converted: {}", orderRequestDto);
    } catch (Exception e) {
      log.error("Error processing message: {}", message, e);
      throw new RuntimeException("Error processing message", e); // Lança exceção para retry
    }
  }
}
