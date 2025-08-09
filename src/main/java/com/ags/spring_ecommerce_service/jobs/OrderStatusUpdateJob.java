package com.ags.spring_ecommerce_service.jobs;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateJob {
  // Roda a cada 60 segundos
  @Scheduled(fixedRate = 5000)
  @SchedulerLock(
      name = "order-status-update", // Nome único para a tarefa
      lockAtMostFor = "55s", // Tempo máximo de execução do lock
      lockAtLeastFor = "4S") // Tempo mínimo para manter o lock
  public void processarDados() {
    System.out.println("Iniciando o processamento de dados...");
    // Sua lógica de negócio aqui
    System.out.println("Processamento de dados concluído.");
  }
}
