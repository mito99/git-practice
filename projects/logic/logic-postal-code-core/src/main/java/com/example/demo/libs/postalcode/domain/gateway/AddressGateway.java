package com.example.demo.libs.postalcode.domain.gateway;

import com.example.demo.libs.postalcode.domain.model.Address;

/**
 * 外部の住所情報提供サービスを抽象化するゲートウェイ。
 * 具体的な接続先（zipcloud等）が何であるかは、このレイヤーでは関知しない。
 */
public interface AddressGateway {
  /**
   * 郵便番号から住所を検索する。
   */
  Address findAddressByPostalCode(String postalCode);
}