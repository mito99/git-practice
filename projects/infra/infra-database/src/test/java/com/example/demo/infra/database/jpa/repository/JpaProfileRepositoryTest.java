package com.example.demo.infra.database.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.example.demo.infra.database.AbstractRepositoryTest;
import com.example.demo.infra.database.jpa.dao.ProfileJpaDao;
import com.example.demo.infra.database.jpa.dao.UserJpaDao;
import com.example.demo.infra.database.jpa.entity.ProfileJpaEntity;
import com.example.demo.infra.database.jpa.entity.UserJpaEntity;
import com.example.demo.libs.postalcode.user.model.Biography;
import com.example.demo.libs.postalcode.user.model.Profile;
import com.example.demo.libs.postalcode.user.model.ProfileId;
import com.example.demo.libs.postalcode.user.model.ProfileImage;
import com.example.demo.libs.postalcode.user.model.ProfileStatus;
import com.example.demo.libs.postalcode.user.model.UserId;

import lombok.val;

@Import(JpaProfileRepository.class)
class JpaProfileRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  JpaProfileRepository repository;

  @Autowired
  ProfileJpaDao jpaDao;

  @Autowired
  UserJpaDao userJpaDao;

  private UserId testUserId;

  @BeforeEach
  void setUp() {
    jpaDao.deleteAll();
    userJpaDao.deleteAll();

    // Create a test user first (foreign key constraint)
    testUserId = UserId.from(UUID.randomUUID());
    val userEntity = new UserJpaEntity();
    userEntity.setId(testUserId.getValue());
    userEntity.setAccountName("testuser");
    userEntity.setEmail("test@example.com");
    userEntity.setPasswordHash("dummyhash");
    userEntity.setFirstName("Test");
    userEntity.setLastName("User");
    userEntity.setPostalCode("1234567");
    userEntity.setAddressLine1("Test Address");
    userJpaDao.save(userEntity);
  }

  @Nested
  class Save {
    @Test
    void 新しいプロフィールを保存できること() {
      // Arrange
      val biography = Biography.of("Software engineer based in Tokyo");
      val profileImage = ProfileImage.of("https://example.com/image.jpg");

      val profile = Profile.builder()
          .userId(testUserId)
          .biography(biography)
          .profileImage(profileImage)
          .status(ProfileStatus.DRAFT)
          .build();

      // Act
      repository.save(profile);

      // Assert
      val saved = jpaDao.findByUserId(testUserId.getValue());
      assertThat(saved).isPresent();
      assertThat(saved.get().getBiography()).isEqualTo("Software engineer based in Tokyo");
      assertThat(saved.get().getProfileImageUrl()).isEqualTo("https://example.com/image.jpg");
      assertThat(saved.get().getStatus()).isEqualTo(ProfileJpaEntity.ProfileStatus.DRAFT);
    }

    @Test
    void 保存時にIDが生成されること() {
      // Arrange
      val profile = Profile.builder()
          .userId(testUserId)
          .biography(Biography.of("Test bio"))
          .status(ProfileStatus.DRAFT)
          .build();

      // Act
      repository.save(profile);

      // Assert
      assertThat(profile.getId()).isNotNull();
      assertThat(profile.getId().getValue()).isNotNull();
    }
  }

  @Nested
  class FindByUserId {
    @Test
    void 存在するプロフィールを取得できること() {
      // Arrange
      val profile = Profile.builder()
          .userId(testUserId)
          .biography(Biography.of("Test bio"))
          .status(ProfileStatus.PUBLISHED)
          .build();
      repository.save(profile);

      // Act
      val found = repository.findByUserId(testUserId);

      // Assert
      assertThat(found).isPresent();
      assertThat(found.get().getUserId()).isEqualTo(testUserId);
      assertThat(found.get().getBiography().toString()).isEqualTo("Test bio");
      assertThat(found.get().getStatus()).isEqualTo(ProfileStatus.PUBLISHED);
    }

    @Test
    void 存在しない場合はEmptyを返すこと() {
      // Act
      val found = repository.findByUserId(testUserId);

      // Assert
      assertThat(found).isEmpty();
    }
  }

  @Nested
  class FindById {
    @Test
    void 存在するプロフィールを取得できること() {
      // Arrange
      val profile = Profile.builder()
          .userId(testUserId)
          .biography(Biography.of("Test bio"))
          .status(ProfileStatus.DRAFT)
          .build();
      repository.save(profile);
      val savedProfileId = profile.getId();

      // Act
      val found = repository.findById(savedProfileId);

      // Assert
      assertThat(found).isPresent();
      assertThat(found.get().getId()).isEqualTo(savedProfileId);
      assertThat(found.get().getBiography().toString()).isEqualTo("Test bio");
    }

    @Test
    void 存在しない場合はEmptyを返すこと() {
      // Act
      val found = repository.findById(ProfileId.from(UUID.randomUUID()));

      // Assert
      assertThat(found).isEmpty();
    }
  }

  @Nested
  class Delete {
    @Test
    void プロフィールを削除できること() {
      // Arrange
      val profile = Profile.builder()
          .userId(testUserId)
          .biography(Biography.of("Test"))
          .status(ProfileStatus.DRAFT)
          .build();
      repository.save(profile);
      val savedProfileId = profile.getId();

      // Act
      repository.delete(savedProfileId);

      // Assert
      val found = jpaDao.findById(savedProfileId.getValue());
      assertThat(found).isEmpty();
    }
  }
}
