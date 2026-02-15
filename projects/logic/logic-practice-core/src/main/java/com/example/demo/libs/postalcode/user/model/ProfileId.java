package com.example.demo.libs.postalcode.user.model;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Getter;

/**
 * Profile entity identifier value object.
 */
public class ProfileId {

  @NonNull
  @Getter
  private final UUID value;

  private ProfileId(@NonNull UUID value) {
    this.value = value;
  }

  /**
   * 値を指定して生成（DBからの復元用など）
   */
  public static ProfileId from(@NonNull UUID value) {
    return new ProfileId(value);
  }

  /**
   * 文字列から復元
   */
  public static ProfileId from(@NonNull String value) {
    final UUID uuid = Objects.requireNonNull(UUID.fromString(value));
    return from(uuid);
  }

  @Override
  public String toString() {
    return Objects.requireNonNull(value).toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProfileId profileId = (ProfileId) o;
    return Objects.equals(value, profileId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
