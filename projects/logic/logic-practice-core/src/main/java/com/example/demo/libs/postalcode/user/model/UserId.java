package com.example.demo.libs.postalcode.user.model;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Getter;

public class UserId {

  @NonNull
  @Getter
  private final UUID value;

  private UserId(@NonNull UUID value) {
    this.value = value;
  }

  /**
   * 値を指定して生成（DBからの復元用など）
   */
  public static UserId from(@NonNull UUID value) {
    return new UserId(value);
  }

  /**
   * 文字列から復元
   */
  public static UserId from(@NonNull String value) {
    final UUID uuid = Objects.requireNonNull(UUID.fromString(value));
    return from(uuid);
  }

  @Override
  public String toString() {
    return Objects.requireNonNull(value).toString();
  }
}
