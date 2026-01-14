package com.example.demo.infra.rest.zipcloud;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.demo.libs.postalcode.address.gateway.AddressGateway;
import com.example.demo.libs.postalcode.address.model.Address;
import com.example.demo.libs.postalcode.address.model.PostalCode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ZipCloudGateway implements AddressGateway {
  private final RestClient restClient;
  private final ObjectMapper objectMapper;

  public ZipCloudGateway(RestClient.Builder builder,
      ObjectMapper objectMapper,
      ZipCloudProperties properties) {
    String baseUrl = properties.url();
    this.restClient = builder.baseUrl(baseUrl).build();
    this.objectMapper = objectMapper;
  }

  @Override
  public List<Address> findAddressByPostalCode(PostalCode postalCode) {
    ZipCloudResponse response = search(postalCode.getValue());

    return response.getResults().stream().map(r -> {
      return Address.builder()
          .postalCode(postalCode)
          .addressLine1(r.getAddress1())
          .addressLine2(r.getAddress2())
          .addressLine3(r.getAddress3())
          .build();
    }).collect(Collectors.toList());
  }

  ZipCloudResponse search(String zipCode) {
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