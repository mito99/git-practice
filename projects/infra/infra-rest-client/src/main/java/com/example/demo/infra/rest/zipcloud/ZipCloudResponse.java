package com.example.demo.infra.rest.zipcloud;

import java.util.List;

import lombok.Data;

@Data
public class ZipCloudResponse {
  private int status;
  private String message;
  private List<Result> results;

  @Data
  public static class Result {
    private String zipcode;
    private String address1; // 都道府県
    private String address2; // 市区町村
    private String address3; // 町域名
    private String kana1;
    private String kana2;
    private String kana3;
  }
}