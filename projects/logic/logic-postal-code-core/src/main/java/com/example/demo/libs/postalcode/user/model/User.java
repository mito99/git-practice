package com.example.demo.libs.postalcode.user.model;

import com.example.demo.libs.postalcode.address.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * users テーブルに対応するドメインエンティティ
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
  private UserId id;
  private EMailAddress email;
  private PersonName fullName;
  private Address address;
}
