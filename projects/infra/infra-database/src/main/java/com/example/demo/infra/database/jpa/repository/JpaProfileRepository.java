package com.example.demo.infra.database.jpa.repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.infra.database.jpa.dao.ProfileJpaDao;
import com.example.demo.infra.database.jpa.entity.ProfileJpaEntity;
import com.example.demo.infra.database.jpa.entity.ProfileJpaEntity.ProfileStatus;
import com.example.demo.libs.postalcode.user.model.Biography;
import com.example.demo.libs.postalcode.user.model.Profile;
import com.example.demo.libs.postalcode.user.model.ProfileId;
import com.example.demo.libs.postalcode.user.model.ProfileImage;
import com.example.demo.libs.postalcode.user.model.UserId;
import com.example.demo.libs.postalcode.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * JPA implementation of ProfileRepository.
 * Handles mapping between domain entities and JPA entities.
 */
@Repository
@RequiredArgsConstructor
public class JpaProfileRepository implements ProfileRepository {

  private final ProfileJpaDao jpaDao;

  @Override
  public void save(Profile profile) {
    val entity = Optional.ofNullable(profile.getId())
        .flatMap(pid -> jpaDao.findById(pid.getValue()))
        .orElseGet(ProfileJpaEntity::new);

    if (profile.getId() != null) {
      entity.setId(profile.getId().getValue());
    } else {
      entity.setId(UUID.randomUUID());
    }

    entity.setUserId(profile.getUserId().getValue());
    entity.setBiography(profile.getBiography() != null ? profile.getBiography().toString() : null);
    entity.setProfileImageUrl(
        profile.getProfileImage() != null ? profile.getProfileImage().toString() : null);
    entity.setStatus(toEntityStatus(profile.getStatus()));

    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(LocalDateTime.now());
    }
    entity.setUpdatedAt(LocalDateTime.now());

    jpaDao.save(entity);

    // Update domain entity with generated ID
    if (profile.getId() == null) {
      profile.setId(ProfileId.from(entity.getId()));
    }
  }

  @Override
  public Optional<Profile> findById(ProfileId profileId) {
    if (profileId == null) {
      return Optional.empty();
    }
    return jpaDao.findById(profileId.getValue())
        .map(this::toDomain);
  }

  @Override
  public Optional<Profile> findByUserId(UserId userId) {
    if (userId == null) {
      return Optional.empty();
    }
    return jpaDao.findByUserId(userId.getValue())
        .map(this::toDomain);
  }

  @Override
  public void delete(ProfileId profileId) {
    if (profileId != null) {
      jpaDao.deleteById(profileId.getValue());
    }
  }

  private Profile toDomain(ProfileJpaEntity entity) {
    val biography = entity.getBiography() != null
        ? Biography.of(entity.getBiography())
        : Biography.of("");

    val profileImage = entity.getProfileImageUrl() != null
        ? ProfileImage.of(entity.getProfileImageUrl())
        : null;

    return Profile.builder()
        .id(ProfileId.from(Objects.requireNonNull(entity.getId())))
        .userId(UserId.from(Objects.requireNonNull(entity.getUserId())))
        .biography(biography)
        .profileImage(profileImage)
        .status(toDomainStatus(entity.getStatus()))
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  private ProfileStatus toEntityStatus(com.example.demo.libs.postalcode.user.model.ProfileStatus domainStatus) {
    if (domainStatus == null) {
      return ProfileStatus.DRAFT;
    }
    return switch (domainStatus) {
      case DRAFT -> ProfileStatus.DRAFT;
      case PUBLISHED -> ProfileStatus.PUBLISHED;
      case ARCHIVED -> ProfileStatus.ARCHIVED;
    };
  }

  private com.example.demo.libs.postalcode.user.model.ProfileStatus toDomainStatus(ProfileStatus entityStatus) {
    if (entityStatus == null) {
      return com.example.demo.libs.postalcode.user.model.ProfileStatus.DRAFT;
    }
    return switch (entityStatus) {
      case DRAFT -> com.example.demo.libs.postalcode.user.model.ProfileStatus.DRAFT;
      case PUBLISHED -> com.example.demo.libs.postalcode.user.model.ProfileStatus.PUBLISHED;
      case ARCHIVED -> com.example.demo.libs.postalcode.user.model.ProfileStatus.ARCHIVED;
    };
  }
}
