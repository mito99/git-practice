package com.example.demo.infra.rest.zipcloud;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ZipCloudGateway {
  private final RestClient restClient;
  private final ObjectMapper objectMapper;

  public ZipCloudGateway(RestClient.Builder builder,
      ObjectMapper objectMapper,
      ZipCloudProperties properties) {
    String baseUrl = properties.url();
    this.restClient = builder.baseUrl(baseUrl).build();
    this.objectMapper = objectMapper;
  }

  public ZipCloudResponse search(String zipCode) {
    try {
      String body = restClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/api/search")
              .queryParam("zipcode", zipCode)
              .build())
          .retrieve()
          .body(String.class);

      return objectMapper.readValue(body, ZipCloudResponse.class);
    } catch (Exception e) {
      throw new IllegalStateException("ZipCloud API response parsing failed", e);
    }
  }
}