package com.ags.spring_ecommerce_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductStatusEnum {
    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE"),
    DISCONTINUED("DISCONTINUED");

    private final String statusName;
}
