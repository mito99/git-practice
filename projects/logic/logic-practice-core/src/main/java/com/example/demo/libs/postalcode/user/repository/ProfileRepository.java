package com.example.demo.libs.postalcode.user.repository;

import java.util.Optional;

import com.example.demo.libs.postalcode.user.model.Profile;
import com.example.demo.libs.postalcode.user.model.ProfileId;
import com.example.demo.libs.postalcode.user.model.UserId;

/**
 * Profile repository interface.
 * Follows Dependency Inversion Principle - domain defines interface.
 */
public interface ProfileRepository {

  void save(Profile profile);

  Optional<Profile> findById(ProfileId profileId);

  Optional<Profile> findByUserId(UserId userId);

  void delete(ProfileId profileId);
}
