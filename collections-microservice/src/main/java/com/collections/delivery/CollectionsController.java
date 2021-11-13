package com.collections.delivery;

import org.springframework.http.ResponseEntity;


public interface CollectionsController {
  ResponseEntity<Object> getAllCollectionsByFilter(String filter);

  ResponseEntity<Object> accessTokenCallback(String code);
}