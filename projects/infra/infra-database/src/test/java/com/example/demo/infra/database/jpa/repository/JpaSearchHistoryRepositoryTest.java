package com.example.demo.infra.database.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.example.demo.infra.database.AbstractRepositoryTest;
import com.example.demo.infra.database.jpa.dao.SearchHistoryJpaDao;
import com.example.demo.infra.database.jpa.entity.SearchHistoryJpaEntity;
import com.example.demo.libs.postalcode.domain.model.Address;
import com.example.demo.libs.postalcode.domain.model.PostalCode;
import com.example.demo.libs.postalcode.domain.model.SearchHistory;

import lombok.val;

@Import(JpaSearchHistoryRepository.class)
class JpaSearchHistoryRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  JpaSearchHistoryRepository repository;

  @Autowired
  SearchHistoryJpaDao jpaDao;

  @Nested
  class Save {
    @Test
    void 正常なドメインモデルを渡した場合_物理エンティティに変換され保存されること() {
      // 前準備 (Arrange): ドメインモデルを作成
      PostalCode pc = new PostalCode("7654321");
      Address addr = Address.builder()
          .postalCode(pc)
          .prefecture("P")
          .city("C")
          .town("T")
          .street("S")
          .build();
      val domain = new SearchHistory(addr, LocalDateTime.of(2025, 2, 2, 10, 0));

      // 実行 (Act): 保存
      repository.save(domain);

      // 検証 (Assert): DBから再取得してラウンドトリップ検証
      List<SearchHistoryJpaEntity> entities = jpaDao.findByPostalCode("7654321");
      assertThat(entities).hasSize(1);
      SearchHistoryJpaEntity e = entities.get(0);
      assertThat(e.getId()).isNotNull();
      assertThat(e.getPostalCode()).isEqualTo("7654321");
      assertThat(e.getPrefecture()).isEqualTo("P");
      assertThat(e.getCity()).isEqualTo("C");
      assertThat(e.getTown()).isEqualTo("T");
      assertThat(e.getSearchAt()).isEqualTo(LocalDateTime.of(2025, 2, 2, 10, 0));
    }
  }

  @Nested
  class FindByPostalCode {
    @Test
    void 該当がない場合_空リストが返ること() {
      // 前準備 (Arrange): DBをクリーン
      jpaDao.deleteAll();

      // 実行 (Act)
      List<SearchHistory> result = repository.findByPostalCode(new PostalCode("0000000"));

      // 検証 (Assert)
      assertThat(result).isEmpty();
    }

    @Test
    void DAOから取得したエンティティがドメインに正しく変換されること() {
      // 前準備 (Arrange): エンティティを直接保存
      jpaDao.deleteAll();
      SearchHistoryJpaEntity e = new SearchHistoryJpaEntity();
      e.setPostalCode("1234567");
      e.setPrefecture("Pref");
      e.setCity("City");
      e.setTown("Town");
      e.setSearchAt(LocalDateTime.of(2024, 12, 31, 23, 59));
      jpaDao.save(e);

      // 実行 (Act)
      List<SearchHistory> result = repository.findByPostalCode(new PostalCode("1234567"));

      // 検証 (Assert)
      assertThat(result).hasSize(1);
      SearchHistory sh = result.get(0);
      assertThat(sh.getAddress().getPostalCode().getValue()).isEqualTo("1234567");
      assertThat(sh.getAddress().getPrefecture()).isEqualTo("Pref");
      assertThat(sh.getAddress().getCity()).isEqualTo("City");
      assertThat(sh.getAddress().getTown()).isEqualTo("Town");
      assertThat(sh.getSearchAt()).isEqualTo(LocalDateTime.of(2024, 12, 31, 23, 59));
    }

    @Test
    void 複数件ある場合_全件がドメインにマッピングされること() {
      // 前準備 (Arrange): 同じ郵便番号で複数件保存
      jpaDao.deleteAll();
      SearchHistoryJpaEntity e1 = new SearchHistoryJpaEntity();
      e1.setPostalCode("1111111");
      e1.setPrefecture("P1");
      e1.setCity("C1");
      e1.setTown("T1");
      e1.setSearchAt(LocalDateTime.of(2025, 1, 1, 1, 1));
      SearchHistoryJpaEntity e2 = new SearchHistoryJpaEntity();
      e2.setPostalCode("1111111");
      e2.setPrefecture("P2");
      e2.setCity("C2");
      e2.setTown("T2");
      e2.setSearchAt(LocalDateTime.of(2025, 1, 2, 2, 2));
      jpaDao.saveAll(List.of(e1, e2));

      // 実行 (Act)
      List<SearchHistory> result = repository.findByPostalCode(new PostalCode("1111111"));

      // 検証 (Assert)
      assertThat(result).hasSize(2);
      assertThat(result).extracting(r -> r.getAddress().getTown()).containsExactlyInAnyOrder("T1", "T2");
    }
  }

}
