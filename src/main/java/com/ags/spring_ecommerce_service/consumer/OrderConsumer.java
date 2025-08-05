package com.ags.spring_ecommerce_service.consumer;

import com.ags.spring_ecommerce_service.dto.request.OrderRequestDto;
import com.ags.spring_ecommerce_service.service.OrderService;
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
public class OrderConsumer extends BaseConsumer {
  private final ObjectMapper objectMapper;
  private final OrderService orderService;

  private static final String SUCCESSFUL_CONVERSION_MESSAGE = "Message successfully converted: {}";
  private static final String ERROR_PROCESSING_MESSAGE = "Error processing message: {}";

  /**
   * Consumidor para criar pedidos a partir de mensagens Kafka. Utiliza RetryableTopic para retries
   * automáticos e DLQ.
   *
   * @param message Mensagem recebida do tópico Kafka.
   */
  // O @RetryableTopic permite retries automáticos e envia mensagens para um tópico DLQ
  // se o número máximo de tentativas for atingido.
  // O sufixo do tópico DLQ é definido como ".DLT" (Dead Letter Topic).
  // O backoff é configurado para aguardar 2 segundos entre as tentativas.
  // O número de tentativas é configurado para 3 (1 tentativa inicial + 2 retries).
  // O grupo de consumidores é definido como "order-create-group".
  @RetryableTopic(
      attempts = "3", // 1 tentativa inicial + 2 retries (default)
      backoff = @Backoff(delay = 2000),
      dltTopicSuffix = ".DLT" // Define o sufixo do tópico DLQ
      )
  @KafkaListener(topics = "${spring.kafka.topics.order-create}", groupId = "order-create-group")
  public void orderCreateConsumer(@Nullable String message) {
    validateMessage(message);

    try {
      OrderRequestDto orderRequestDto = objectMapper.readValue(message, OrderRequestDto.class);
      log.info(SUCCESSFUL_CONVERSION_MESSAGE, orderRequestDto);

      orderService.createOrder(orderRequestDto);
    } catch (IllegalArgumentException e) {
      log.error(ERROR_PROCESSING_MESSAGE, message, e);
    } catch (Exception e) {
      log.error(ERROR_PROCESSING_MESSAGE, message, e);
      throw new RuntimeException("Error creating order", e); // Lança exceção para retry
    }
  }

  /**
   * Consumidor para atualizar pedidos a partir de mensagens Kafka. Utiliza RetryableTopic para
   * retries automáticos e DLQ.
   *
   * @param message Mensagem recebida do tópico Kafka.
   */
  // O @RetryableTopic permite retries automáticos e envia mensagens para um tópico DLQ
  // se o número máximo de tentativas for atingido.
  // O sufixo do tópico DLQ é definido como ".DLT" (Dead Letter Topic).
  // O backoff é configurado para aguardar 2 segundos entre as tentativas.
  // O número de tentativas é configurado para 3 (1 tentativa inicial + 2 retries).
  // O grupo de consumidores é definido como "order-update-group".
  @RetryableTopic(
      attempts = "3", // 1 tentativa inicial + 2 retries (default)
      backoff = @Backoff(delay = 2000),
      dltTopicSuffix = ".DLT" // Define o sufixo do tópico DLQ
      )
  @KafkaListener(topics = "${spring.kafka.topics.order-update}", groupId = "order-update-group")
  public void orderUpdateConsumer(@Nullable String message) {
    validateMessage(message);

    try {
      OrderRequestDto orderRequestDto = objectMapper.readValue(message, OrderRequestDto.class);
      log.info(SUCCESSFUL_CONVERSION_MESSAGE, orderRequestDto);

      orderService.updateOrder(orderRequestDto);
    } catch (IllegalArgumentException e) {
      log.error(ERROR_PROCESSING_MESSAGE, message, e);
    } catch (Exception e) {
      log.error(ERROR_PROCESSING_MESSAGE, message, e);
      throw new RuntimeException("Error updating order", e); // Lança exceção para retry
    }
  }
}
