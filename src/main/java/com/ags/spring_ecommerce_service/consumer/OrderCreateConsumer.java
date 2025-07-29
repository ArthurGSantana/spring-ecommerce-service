package com.ags.spring_ecommerce_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateConsumer {
  @RetryableTopic(
      attempts = "3", // 1 tentativa inicial + 2 retries
      backoff = @Backoff(delay = 2000),
      dltTopicSuffix = ".DLT" // Define o sufixo do tópico DLQ
      )
  @KafkaListener(topics = "${spring.kafka.topics.order-create}", groupId = "order-create-group")
  public void consume(String message) {
    // Lógica para processar a mensagem recebida
    System.out.println("Mensagem recebida: " + message);
    // Aqui você pode adicionar a lógica para criar o pedido
  }
}
