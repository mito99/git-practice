package com.example.demo.infra.rest.zipcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "gateway.zipcloud")
public record ZipCloudProperties(String url) {
  @ConstructorBinding
  public ZipCloudProperties {
  }
}