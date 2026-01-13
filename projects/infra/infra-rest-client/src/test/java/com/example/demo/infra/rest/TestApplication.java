package com.example.demo.infra.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.infra.rest.zipcloud.ZipCloudProperties;

@SpringBootApplication
@EnableConfigurationProperties(ZipCloudProperties.class)
@TestPropertySource(properties = {
    "spring.config.import=optional:classpath:infra-rest-client-config.yaml"
})
public class TestApplication {

}
