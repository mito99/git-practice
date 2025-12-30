package com.example.demo.infra.database;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.infra.database.jpa.entity.SearchHistoryJpaEntity;
import com.example.demo.infra.database.jpa.repository.SearchHistoryJpaDao;
import com.example.demo.libs.postalcode.domain.model.Address;
import com.example.demo.libs.postalcode.domain.model.PostalCode;
import com.example.demo.libs.postalcode.domain.model.SearchHistory;
import com.example.demo.libs.postalcode.domain.repository.SearchHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Component
@RequiredArgsConstructor
public class JpaSearchHistoryRepository implements SearchHistoryRepository {

  private final SearchHistoryJpaDao jpaDao;

  @Override
  public void save(SearchHistory domainModel) {
    val entity = new SearchHistoryJpaEntity();
    val address = domainModel.getAddress();
    entity.setPostalCode(address.getPostalCode().getValue());
    entity.setPrefecture(address.getPrefecture());
    entity.setCity(address.getCity());
    entity.setTown(address.getTown());
    entity.setSearchAt(domainModel.getSearchAt());

    jpaDao.save(entity);
  }

  @Override
  public List<SearchHistory> findByPostalCode(PostalCode postalCode) {
    val entities = jpaDao.findByPostalCode(postalCode.getValue());
    return entities.stream()
        .map(entity -> new SearchHistory(
            Address.builder()
                .postalCode(new PostalCode(entity.getPostalCode()))
                .prefecture(entity.getPrefecture())
                .city(entity.getCity())
                .town(entity.getTown())
                .build(),
            entity.getSearchAt()))
        .toList();
  }
}