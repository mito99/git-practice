package com.example.demo.libs.postalcode.user.usecase;

import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.user.model.Profile;
import com.example.demo.libs.postalcode.user.model.ProfileId;
import com.example.demo.libs.postalcode.user.model.UserId;
import com.example.demo.libs.postalcode.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Use case for retrieving user profiles.
 */
@Service
@RequiredArgsConstructor
public class GetUserProfileUseCase {

  private final ProfileRepository profileRepository;

  /**
   * Gets a profile by ID.
   */
  public Profile getById(ProfileId profileId) {
    return profileRepository.findById(profileId)
        .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + profileId));
  }

  /**
   * Gets a profile by user ID.
   */
  public Profile getByUserId(UserId userId) {
    return profileRepository.findByUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + userId));
  }
}
