package com.example.demo.infra.rest.zipcloud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.example.demo.infra.rest.InfraRestClientTest;

import lombok.val;

@InfraRestClientTest
@AutoConfigureMockRestServiceServer
public class ZipCloudGatewayTest {

  @Autowired
  private ZipCloudGateway zipCloudGateway;

  @Autowired
  private MockRestServiceServer server; // Mockサーバ

  @Test
  void search_正常系() throws Exception {
    String json = """
        {
          "status": 200,
          "message": null,
          "results": [
            {
              "zipcode": "1234567",
              "address1": "東京都",
              "address2": "新宿区",
              "address3": "西新宿",
              "kana1": "トウキョウト",
              "kana2": "シンジュクク",
              "kana3": "ニシシンジュク"
            }
          ]
        }
        """;

    // Mock の振る舞いを定義
    val exptectedUrl = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=1234567";
    this.server.expect(requestTo(exptectedUrl))
        .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

    // 実行
    val result = zipCloudGateway.search("1234567");

    // 検証
    assertThat(result).isNotNull();
    this.server.verify();
  }
}
