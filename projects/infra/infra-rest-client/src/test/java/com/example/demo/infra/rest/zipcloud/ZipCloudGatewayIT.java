package com.example.demo.infra.rest.zipcloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.infra.rest.InfraRestClientTest;

/**
 * ZipCloud API との実際の疎通を確認するための試作コード。
 */
@InfraRestClientTest
class ZipCloudGatewayIT {

  @Autowired
  private ZipCloudGateway gateway;

  @Test
  void 実際のAPIから住所が取得できることを確認する() {
    var response = gateway.search("7830060");

    System.out.println("=== API Response Trial ===");
    System.out.println("Status: " + response.getStatus());
    System.out.println("Data: " + response.getResults());
    System.out.println("==========================");
  }
}