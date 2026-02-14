package com.example.demo.libs.postalcode.user.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EMailAddress {

  private final String value;

  @Override
  public String toString() {
    return value;
  }

}
