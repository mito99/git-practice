package com.example.demo.infra.database.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "search_history")
@Getter
@Setter
public class SearchHistoryJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "postal_code", nullable = false, length = 7)
  private String postalCode;

  @Column(name = "prefecture", nullable = false)
  private String prefecture;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "town", nullable = false)
  private String town;

  @Column(name = "search_at", nullable = false)
  private LocalDateTime searchAt;
}