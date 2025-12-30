package com.example.demo.libs.postalcode.domain.repository;

import java.util.List;

import com.example.demo.libs.postalcode.domain.model.PostalCode;
import com.example.demo.libs.postalcode.domain.model.SearchHistory;

public interface SearchHistoryRepository {
  void save(SearchHistory searchHistory);

  List<SearchHistory> findByPostalCode(PostalCode postalCode);
}