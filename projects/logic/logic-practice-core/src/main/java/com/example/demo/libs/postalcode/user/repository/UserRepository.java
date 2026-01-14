package com.example.demo.libs.postalcode.user.repository;

import java.util.List;

import com.example.demo.libs.postalcode.user.model.EMailAddress;
import com.example.demo.libs.postalcode.user.model.User;
import com.example.demo.libs.postalcode.user.model.UserId;

public interface UserRepository {

  List<User> findByAll();

  User findById(UserId userId);

  User findByEmailAddress(EMailAddress emailAddress);

  void save(User user);
}
