package com.example.demo.libs.postalcode.address.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.address.gateway.AddressGateway;
import com.example.demo.libs.postalcode.address.model.Address;
import com.example.demo.libs.postalcode.address.model.PostalCode;

@Service
public class SearchAddressUseCase {

  @Autowired
  private AddressGateway addressGateway;

  public List<Address> execute(PostalCode postalCode) {
    return addressGateway.findAddressByPostalCode(postalCode);
  }
}