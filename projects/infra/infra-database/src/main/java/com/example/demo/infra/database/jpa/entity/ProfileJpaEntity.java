package com.example.demo.infra.database.jpa.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class ProfileJpaEntity {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "biography", length = 500)
  private String biography;

  @Column(name = "profile_image_url")
  private String profileImageUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private ProfileStatus status;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public enum ProfileStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED
  }
}
