package com.example.demo.libs.postalcode.user.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.libs.postalcode.user.model.Biography;
import com.example.demo.libs.postalcode.user.model.Profile;
import com.example.demo.libs.postalcode.user.model.ProfileId;
import com.example.demo.libs.postalcode.user.model.ProfileImage;
import com.example.demo.libs.postalcode.user.model.ProfileStatus;
import com.example.demo.libs.postalcode.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Use case for updating an existing user profile.
 * NOTE: This method has high cyclomatic complexity (~9) and could be refactored.
 */
@Service
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {

  private final ProfileRepository profileRepository;

  /**
   * Updates a user profile with the provided data.
   * Only updates non-null fields.
   *
   * @param profileId the profile ID
   * @param newBiography the new biography (optional)
   * @param newProfileImageUrl the new profile image URL (optional)
   * @param publish whether to publish the profile after update
   * @return the updated profile
   * @throws IllegalArgumentException if profile not found
   */
  public Profile execute(ProfileId profileId, Biography newBiography,
      String newProfileImageUrl, boolean publish) {

    Optional<Profile> optProfile = profileRepository.findById(profileId);
    if (optProfile.isEmpty()) {
      throw new IllegalArgumentException("Profile not found: " + profileId);
    }

    Profile profile = optProfile.get();

    // Update biography if provided
    if (newBiography != null) {
      profile.updateBiography(newBiography);
    }

    // Update profile image if provided
    if (newProfileImageUrl != null && !newProfileImageUrl.isBlank()) {
      ProfileImage newImage = ProfileImage.of(newProfileImageUrl);
      profile.updateProfileImage(newImage);
    }

    // Handle publish status
    if (publish) {
      if (profile.canPublish()) {
        profile.publish();
      }
    } else {
      // If unpublishing, revert to draft
      if (profile.isPublished()) {
        profile.setStatus(ProfileStatus.DRAFT);
      }
    }

    profileRepository.save(profile);
    return profile;
  }
}
