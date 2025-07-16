package com.ags.spring_ecommerce_service.utils;

public class SkuGenerator {
  public static String generate(String productName) {
    var randomNumber = (int) (Math.random() * 10000);
    var initials = productName.replaceAll("[^A-Za-z]", "").substring(0, 3).toUpperCase();
    return initials + String.format("%04d", randomNumber);
  }
}
