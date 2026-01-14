package com.example.demo.infra.database.jpa.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserJpaEntity {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "account_name", nullable = false, length = 50)
  private String accountName;

  @Column(name = "email", nullable = false, length = 255)
  private String email;

  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Column(name = "postal_code", nullable = false, length = 7)
  private String postalCode;

  @Column(name = "address_line1", nullable = false, length = 255)
  private String addressLine1;

  @Column(name = "address_line2", length = 255)
  private String addressLine2;

  @Column(name = "address_line3", length = 255)
  private String addressLine3;
}
