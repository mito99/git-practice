package com.example.demo.libs.postalcode.usecase;

import com.example.demo.libs.postalcode.domain.gateway.AddressGateway;
import com.example.demo.libs.postalcode.domain.model.Address;

public class SearchAddressUseCase {
  private final AddressGateway addressGateway;

  public SearchAddressUseCase(AddressGateway addressGateway) {
    this.addressGateway = addressGateway;
  }

  public Address execute(String postalCode) {
    return addressGateway.findAddressByPostalCode(postalCode);
  }
}