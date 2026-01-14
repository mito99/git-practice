package com.example.demo.libs.postalcode.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.user.model.User;
import com.example.demo.libs.postalcode.user.repository.UserRepository;

@Service
public class RegisterUserUseCase {

  @Autowired
  private UserRepository userRepository;

  public void execute(User user) {
    userRepository.save(user);
  }
}
