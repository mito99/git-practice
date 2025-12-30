package com.example.demo.libs.postalcode.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostalCode {
  @Getter
  private final String value;
}