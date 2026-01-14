package com.example.demo.libs.postalcode.address.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class Address {
  @Getter
  private final PostalCode postalCode;
  @Getter
  private final String addressLine1;
  @Getter
  private final String addressLine2;
  @Getter
  private final String addressLine3;
}