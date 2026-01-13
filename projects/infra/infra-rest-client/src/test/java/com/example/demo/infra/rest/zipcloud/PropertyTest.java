package com.example.demo.infra.rest.zipcloud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.infra.rest.InfraRestClientTest;

@InfraRestClientTest
class PropertyTest {

  @Autowired
  private ZipCloudProperties properties; // @Value ではなくこちらを使う

  @Test
  void test() {
    // これで https://zipcloud.ibsnet.co.jp が取れるはずです
    System.out.println("Property URL: " + properties.url());
    assertEquals("https://zipcloud.ibsnet.co.jp", properties.url());
  }
}