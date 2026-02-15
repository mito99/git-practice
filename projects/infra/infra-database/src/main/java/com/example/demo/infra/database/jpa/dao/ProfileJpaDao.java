package com.example.demo.infra.database.jpa.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.infra.database.jpa.entity.ProfileJpaEntity;

@Repository
public interface ProfileJpaDao extends JpaRepository<ProfileJpaEntity, UUID> {

  Optional<ProfileJpaEntity> findByUserId(UUID userId);

  void deleteById(UUID id);
}
