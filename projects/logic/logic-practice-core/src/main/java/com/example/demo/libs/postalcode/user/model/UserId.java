package com.example.demo.libs.postalcode.user.model;

import java.util.UUID;

public class UserId {

  private final UUID value;

  private UserId(UUID value) {
    if (value == null) {
      throw new IllegalArgumentException("IDの値は必須です");
    }
    this.value = value;
  }

  /**
   * 値を指定して生成（DBからの復元用など）
   */
  public static UserId from(UUID value) {
    return new UserId(value);
  }

  /**
   * 文字列から復元
   */
  public static UserId from(String value) {
    return from(UUID.fromString(value));
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
