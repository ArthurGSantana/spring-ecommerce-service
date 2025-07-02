package com.ags.spring_ecommerce_service.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private int status;
  private String error;
  private String message;
  private LocalDateTime timestamp;
}
