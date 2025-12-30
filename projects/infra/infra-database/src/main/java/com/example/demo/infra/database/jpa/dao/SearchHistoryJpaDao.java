package com.example.demo.infra.database.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.infra.database.jpa.entity.SearchHistoryJpaEntity;

@Repository
public interface SearchHistoryJpaDao extends JpaRepository<SearchHistoryJpaEntity, Long> {
  List<SearchHistoryJpaEntity> findByPostalCode(String postalCode);
}