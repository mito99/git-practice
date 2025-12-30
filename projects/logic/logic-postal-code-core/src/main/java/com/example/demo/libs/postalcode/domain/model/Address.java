package com.example.demo.libs.postalcode.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class Address {
  @Getter
  private final String street;
  @Getter
  private final String city;
  @Getter
  private final String town;
  @Getter
  private final String prefecture;
  @Getter
  private final PostalCode postalCode;
}