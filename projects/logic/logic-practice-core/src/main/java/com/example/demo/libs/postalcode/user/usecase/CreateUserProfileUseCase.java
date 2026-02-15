package com.example.demo.libs.postalcode.user.usecase;

import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.user.model.Biography;
import com.example.demo.libs.postalcode.user.model.Profile;
import com.example.demo.libs.postalcode.user.model.ProfileImage;
import com.example.demo.libs.postalcode.user.model.ProfileStatus;
import com.example.demo.libs.postalcode.user.model.UserId;
import com.example.demo.libs.postalcode.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Use case for creating a new user profile.
 */
@Service
@RequiredArgsConstructor
public class CreateUserProfileUseCase {

  private final ProfileRepository profileRepository;

  /**
   * Creates a new profile for the given user.
   *
   * @param userId the user ID
   * @param biography the biography text
   * @param profileImageUrl the profile image URL (optional)
   * @return the created profile
   */
  public Profile execute(UserId userId, Biography biography, String profileImageUrl) {
    var existingProfile = profileRepository.findByUserId(userId);
    if (existingProfile.isPresent()) {
      throw new IllegalStateException("Profile already exists for user: " + userId);
    }

    var profileImage = profileImageUrl != null
        ? ProfileImage.of(profileImageUrl)
        : null;

    var profile = Profile.builder()
        .userId(userId)
        .biography(biography)
        .profileImage(profileImage)
        .status(ProfileStatus.DRAFT)
        .build();

    profileRepository.save(profile);
    return profile;
  }
}
