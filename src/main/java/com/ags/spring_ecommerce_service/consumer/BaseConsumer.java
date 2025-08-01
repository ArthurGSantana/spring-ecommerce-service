package com.ags.spring_ecommerce_service.consumer;

public abstract class BaseConsumer {
  public void validateMessage(String message) {
    if (message == null || message.isEmpty()) {
      throw new IllegalArgumentException("Message cannot be null or empty");
    }
  }
}
