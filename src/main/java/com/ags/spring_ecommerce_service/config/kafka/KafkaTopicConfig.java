package com.ags.spring_ecommerce_service.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
  @Value("${spring.kafka.topics.order-create}")
  private String orderCreateTopic;

  @Bean
  public NewTopic createOrderTopic() {
    return TopicBuilder.name(orderCreateTopic) // Nome do tópico
        .partitions(3) // Exemplo: 3 partições para o tópico principal
        .replicas(1) // Fator de replicação (1 para cluster Docker local de 1 broker)
        .build();
  }

  //  // Define o tópico DLQ para mensagens de pedidos que falharam
  //  @Bean
  //  public NewTopic topicoPedidosDlt() {
  //    // Usa o nome padrão que o @RetryableTopic ou DeadLetterPublishingRecoverer usaria
  //    return TopicBuilder.name("topico-pedidos-criados.DLT")
  //        .partitions(1) // DLQs geralmente não precisam de muitas partições
  //        .replicas(1)   // Fator de replicação
  //        // Opcional: Configurar uma retenção maior para mensagens DLQ
  //        // .config("retention.ms", "2592000000") // 30 dias
  //        .build();
  //  }
}
