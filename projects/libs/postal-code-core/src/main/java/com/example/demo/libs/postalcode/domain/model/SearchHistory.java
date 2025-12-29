package com.example.demo.libs.postalcode.domain.model;

import java.util.Date;

public class SearchHistory {
  private Address address;
  private Date searchDate;

  public SearchHistory(Address address, Date searchDate) {
    this.address = address;
    this.searchDate = searchDate;
  }

  public Address getAddress() {
    return address;
  }

  public Date getSearchDate() {
    return searchDate;
  }
}