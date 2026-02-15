package com.example.demo.libs.postalcode.user.model;

import lombok.Getter;

/**
 * Biography value object with validation.
 * MAX_LENGTH is 500 characters.
 */
@Getter
public class Biography {

  private static final int MAX_LENGTH = 500;

  private final String value;

  private Biography(String value) {
    if (value == null || value.isBlank()) {
      this.value = "";
    } else if (value.length() > MAX_LENGTH) {
      // NOTE: Silently truncates values exceeding MAX_LENGTH
      // This may cause data inconsistency as users won't be aware of the truncation
      this.value = value.substring(0, MAX_LENGTH);
    } else {
      this.value = value;
    }
  }

  public static Biography of(String value) {
    return new Biography(value);
  }

  public boolean isEmpty() {
    return value == null || value.isEmpty();
  }

  public int length() {
    return value.length();
  }

  @Override
  public String toString() {
    return value;
  }
}
