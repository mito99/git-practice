package com.example.demo.libs.postalcode.user.usecase;

import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.user.model.EMailAddress;
import com.example.demo.libs.postalcode.user.model.User;
import com.example.demo.libs.postalcode.user.repository.UserRepository;

@Service
public class FindUserByEmailAddressUseCase {

  private UserRepository userRepository;

  public User execute(EMailAddress emailAddress) {
    return userRepository.findByEmailAddress(emailAddress);
  }
}
