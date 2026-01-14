package com.example.demo.libs.postalcode.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonName {

  @Getter
  private final String firstName;
  @Getter
  private final String lastName;
}
