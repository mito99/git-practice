package com.example.demo.infra.database.jpa.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.infra.database.jpa.entity.UserJpaEntity;

@Repository
public interface UserJpaDao extends JpaRepository<UserJpaEntity, UUID> {
  UserJpaEntity findByEmail(String email);
}
