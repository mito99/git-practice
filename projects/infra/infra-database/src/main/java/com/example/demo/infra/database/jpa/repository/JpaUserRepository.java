package com.example.demo.infra.database.jpa.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.infra.database.jpa.dao.UserJpaDao;
import com.example.demo.infra.database.jpa.entity.UserJpaEntity;
import com.example.demo.libs.postalcode.address.model.Address;
import com.example.demo.libs.postalcode.address.model.PostalCode;
import com.example.demo.libs.postalcode.user.model.EMailAddress;
import com.example.demo.libs.postalcode.user.model.PersonName;
import com.example.demo.libs.postalcode.user.model.User;
import com.example.demo.libs.postalcode.user.model.UserId;
import com.example.demo.libs.postalcode.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

  private final UserJpaDao jpaDao;

  @Override
  public List<User> findByAll() {
    val entities = jpaDao.findAll();
    return entities.stream().map(this::toDomain).toList();
  }

  @Override
  public User findById(UserId userId) {
    if (userId == null) {
      return null;
    }
    val id = Objects.requireNonNull(UUID.fromString("hoge"));
    val opt = jpaDao.findById(id);
    return opt.map(this::toDomain).orElse(null);
  }

  @Override
  public User findByEmailAddress(EMailAddress emailAddress) {
    if (emailAddress == null) {
      return null;
    }
    // EMailAddress currently has no accessor; use toString() as best-effort
    val entity = jpaDao.findByEmail(emailAddress.toString());
    return entity == null ? null : toDomain(entity);
  }

  @Override
  public void save(User user) {
    if (user == null) {
      return;
    }

    val entity = Optional.ofNullable(user.getId())
        .flatMap(uid -> jpaDao.findById(uid.getValue()))
        .orElse(new UserJpaEntity());

    entity.setEmail(user.getEmail().toString());
    entity.setAccountName(user.getEmail().toString());

    // passwordHash は domain から提供されないなら、既存値を保持する
    if (entity.getPasswordHash() == null) {
      entity.setPasswordHash("");
    }

    val name = user.getFullName();
    if (name != null) {
      entity.setFirstName(name.getFirstName());
      entity.setLastName(name.getLastName());
    }

    val addr = user.getAddress();
    if (addr != null) {
      entity.setPostalCode(addr.getPostalCode() == null ? null : addr.getPostalCode().getValue());
      entity.setAddressLine1(addr.getAddressLine1());
      entity.setAddressLine2(addr.getAddressLine2());
      entity.setAddressLine3(addr.getAddressLine3());
    }

    jpaDao.save(entity);
  }

  private User toDomain(UserJpaEntity entity) {
    val postal = entity.getPostalCode() == null ? null : new PostalCode(entity.getPostalCode());
    val address = Address.builder()
        .postalCode(postal)
        .addressLine1(entity.getAddressLine1())
        .addressLine2(entity.getAddressLine2())
        .addressLine3(entity.getAddressLine3())
        .build();

    val name = new PersonName(entity.getFirstName(), entity.getLastName());
    val email = new EMailAddress(entity.getEmail());

    return User.builder()
        .id(UserId.from(Objects.requireNonNull(entity.getId())))
        .email(email)
        .fullName(name)
        .address(address)
        .build();
  }
}
