package com.ags.spring_ecommerce_service.handlers;

import lombok.Data;

@Data
public class EventData<T> {
  private String op;
  private T before;
  private T after;
}
