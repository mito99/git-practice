package com.example.demo.libs.postalcode.user.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Profile aggregate root entity.
 * Represents a user's profile information with biography and image.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
  private ProfileId id;
  private UserId userId;
  private Biography biography;
  private ProfileImage profileImage;
  private ProfileStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  /**
   * Updates the profile biography.
   */
  public void updateBiography(Biography newBiography) {
    if (newBiography == null) {
      throw new IllegalArgumentException("Biography cannot be null");
    }
    this.biography = newBiography;
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Updates the profile image.
   */
  public void updateProfileImage(ProfileImage newImage) {
    if (newImage == null) {
      throw new IllegalArgumentException("Profile image cannot be null");
    }
    this.profileImage = newImage;
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Publishes the profile.
   */
  public void publish() {
    if (this.status == ProfileStatus.PUBLISHED) {
      return;
    }
    // Validate required fields before publishing
    if (this.biography == null || this.biography.isEmpty()) {
      throw new IllegalStateException("Cannot publish profile without biography");
    }
    this.status = ProfileStatus.PUBLISHED;
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Archives the profile.
   */
  public void archive() {
    if (this.status == ProfileStatus.ARCHIVED) {
      return;
    }
    this.status = ProfileStatus.ARCHIVED;
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Checks if the profile is published.
   */
  public boolean isPublished() {
    return this.status == ProfileStatus.PUBLISHED;
  }

  /**
   * Checks if the profile can be published.
   */
  public boolean canPublish() {
    return this.biography != null && !this.biography.isEmpty();
  }

  /**
   * Sets the profile status.
   */
  public void setStatus(ProfileStatus status) {
    this.status = status;
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Sets the profile ID (for infrastructure layer).
   */
  public void setId(ProfileId id) {
    this.id = id;
  }
}
