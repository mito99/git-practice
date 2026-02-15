package com.example.demo.libs.postalcode.user.model;

import java.net.URI;

import lombok.Getter;

/**
 * Profile image URL value object with validation.
 */
@Getter
public class ProfileImage {

  private final URI url;

  private ProfileImage(URI url) {
    if (url == null) {
      throw new IllegalArgumentException("Profile image URL cannot be null");
    }
    String urlString = url.toString();
    if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
      throw new IllegalArgumentException("Profile image URL must use HTTP or HTTPS protocol");
    }
    this.url = url;
  }

  public static ProfileImage of(String urlString) {
    if (urlString == null || urlString.isBlank()) {
      return null;
    }
    return new ProfileImage(URI.create(urlString));
  }

  @Override
  public String toString() {
    return url.toString();
  }
}
