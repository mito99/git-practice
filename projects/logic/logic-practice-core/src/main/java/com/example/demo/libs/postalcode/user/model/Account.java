package com.example.demo.libs.postalcode.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Account {

  @Getter
  private final String accountName;

  @Getter
  private final String passwordHash;
}
