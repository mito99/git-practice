package com.example.demo.libs.postalcode.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.user.model.User;
import com.example.demo.libs.postalcode.user.model.UserId;
import com.example.demo.libs.postalcode.user.repository.UserRepository;

@Service
public class FindUserByIdUseCase {

  @Autowired
  private UserRepository userRepository;

  public User execute(UserId userId) {
    return userRepository.findById(userId);
  }
}