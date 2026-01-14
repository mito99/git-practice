package com.example.demo.libs.postalcode.address.gateway;

import java.util.List;

import com.example.demo.libs.postalcode.address.model.Address;
import com.example.demo.libs.postalcode.address.model.PostalCode;

/**
 * 外部の住所情報提供サービスを抽象化するゲートウェイ。
 * 具体的な接続先（zipcloud等）が何であるかは、このレイヤーでは関知しない。
 */
public interface AddressGateway {
  /**
   * 郵便番号から住所を検索する。
   */
  List<Address> findAddressByPostalCode(PostalCode postalCode);
}