package com.example.demo.libs.postalcode.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchHistory {
  @Getter
  private final Address address;
  @Getter
  private final LocalDateTime searchAt;
}