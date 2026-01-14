package com.example.demo.infra.database.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.example.demo.infra.database.AbstractRepositoryTest;
import com.example.demo.infra.database.jpa.dao.UserJpaDao;
import com.example.demo.infra.database.jpa.entity.UserJpaEntity;
import com.example.demo.libs.postalcode.address.model.Address;
import com.example.demo.libs.postalcode.address.model.PostalCode;
import com.example.demo.libs.postalcode.user.model.EMailAddress;
import com.example.demo.libs.postalcode.user.model.PersonName;
import com.example.demo.libs.postalcode.user.model.User;
import com.example.demo.libs.postalcode.user.model.UserId;

import lombok.val;

@Import(JpaUserRepository.class)
class JpaUserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  JpaUserRepository repository;

  @Autowired
  UserJpaDao jpaDao;

  @Nested
  class Save {
    @Test
    void 正常なドメインモデルを渡した場合_物理エンティティに変換され保存されること() {
      // Arrange
      jpaDao.deleteAll();
      UUID id = UUID.randomUUID();
      val uid = UserId.from(id);
      val email = new EMailAddress();
      val name = new PersonName("First", "Last");
      val addr = Address.builder()
          .postalCode(new PostalCode("7654321"))
          .addressLine1("A1")
          .addressLine2("A2")
          .addressLine3("A3")
          .build();

      val domain = User.builder()
          .id(uid)
          .email(email)
          .fullName(name)
          .address(addr)
          .build();

      // Act
      repository.save(domain);

      // Assert
      UserJpaEntity e = jpaDao.findByEmail(email.toString());
      assertThat(e).isNotNull();
      assertThat(e.getId()).isEqualTo(id);
      assertThat(e.getFirstName()).isEqualTo("First");
      assertThat(e.getLastName()).isEqualTo("Last");
      assertThat(e.getPostalCode()).isEqualTo("7654321");
      assertThat(e.getAddressLine1()).isEqualTo("A1");
      assertThat(e.getAddressLine2()).isEqualTo("A2");
      assertThat(e.getAddressLine3()).isEqualTo("A3");
    }
  }

  @Nested
  class FindByEmail {
    @Test
    void 該当がない場合_nullが返ること() {
      jpaDao.deleteAll();
      val res = repository.findByEmailAddress(new EMailAddress());
      assertThat(res).isNull();
    }

    @Test
    void DAOから取得したエンティティがドメインに正しく変換されること() {
      // Arrange
      jpaDao.deleteAll();
      UUID id = UUID.randomUUID();
      val email = new EMailAddress();
      String emailStr = email.toString();
      UserJpaEntity e = new UserJpaEntity();
      e.setId(id);
      e.setAccountName("test");
      e.setPasswordHash("passwordhash");
      e.setEmail(emailStr);
      e.setFirstName("F");
      e.setLastName("L");
      e.setPostalCode("1234567");
      e.setAddressLine1("AA");
      jpaDao.save(e);

      // Act
      val result = repository.findByEmailAddress(email);

      // Assert
      assertThat(result).isNotNull();
      assertThat(result.getId().toString()).isEqualTo(id.toString());
      assertThat(result.getFullName().getFirstName()).isEqualTo("F");
      assertThat(result.getFullName().getLastName()).isEqualTo("L");
      assertThat(result.getAddress().getPostalCode().getValue()).isEqualTo("1234567");
    }
  }

  @Nested
  class FindById {
    @Test
    void 該当がない場合_nullが返ること() {
      jpaDao.deleteAll();
      val res = repository.findById(null);
      assertThat(res).isNull();
    }

    @Test
    void DAOから取得したエンティティがドメインに正しく変換されること() {
      jpaDao.deleteAll();
      UUID id = UUID.randomUUID();
      UserJpaEntity e = new UserJpaEntity();
      e.setId(id);
      e.setAccountName("test");
      e.setPasswordHash("passwordhash");
      e.setEmail("test@test.com");
      e.setFirstName("FN");
      e.setLastName("LN");
      e.setPostalCode("2222222");
      e.setAddressLine1("address1");
      e.setAddressLine2("address2");
      e.setAddressLine3("address3");
      jpaDao.save(e);

      val result = repository.findById(UserId.from(id));

      assertThat(result).isNotNull();
      assertThat(result.getId().toString()).isEqualTo(id.toString());
      assertThat(result.getFullName().getFirstName()).isEqualTo("FN");
      assertThat(result.getAddress().getPostalCode().getValue()).isEqualTo("2222222");
    }
  }

  @Nested
  class FindAll {
    @Test
    void 複数件ある場合_全件がドメインにマッピングされること() {
      jpaDao.deleteAll();
      UserJpaEntity e1 = new UserJpaEntity();
      e1.setId(UUID.randomUUID());
      e1.setAccountName("test1");
      e1.setPasswordHash("passwordhash");
      e1.setEmail("test1@test.com");
      e1.setFirstName("A1");
      e1.setLastName("B1");
      e1.setPostalCode("2222222");
      e1.setAddressLine1("address1");
      e1.setAddressLine2("address2");
      e1.setAddressLine3("address3");

      UserJpaEntity e2 = new UserJpaEntity();
      e2.setId(UUID.randomUUID());
      e2.setAccountName("test2");
      e2.setPasswordHash("passwordhash");
      e2.setEmail("test2@test.com");
      e2.setFirstName("A2");
      e2.setLastName("B2");
      e2.setPostalCode("2222222");
      e2.setAddressLine1("address1");
      e2.setAddressLine2("address2");
      e2.setAddressLine3("address3");
      jpaDao.saveAll(List.of(e1, e2));

      List<User> result = repository.findByAll();

      assertThat(result).hasSize(2);
      assertThat(result).extracting(r -> r.getFullName().getFirstName()).containsExactlyInAnyOrder("A1", "A2");
    }
  }

}
