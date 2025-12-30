package com.example.demo.libs.postalcode.domain.repository;

import java.util.List;

import com.example.demo.libs.postalcode.domain.model.Address;
import com.example.demo.libs.postalcode.domain.model.SearchHistory;

public interface SearchHistoryRepository {
  void save(SearchHistory searchHistory);

  List<SearchHistory> findByAddress(Address address);
}