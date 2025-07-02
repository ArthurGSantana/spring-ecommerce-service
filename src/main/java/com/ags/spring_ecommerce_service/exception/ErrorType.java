package com.ags.spring_ecommerce_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation Error"),
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Parameter"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
  DATA_CONFLICT(HttpStatus.CONFLICT, "Data Conflict"),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access Denied"),
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

  private final HttpStatus status;
  private final String error;
}
