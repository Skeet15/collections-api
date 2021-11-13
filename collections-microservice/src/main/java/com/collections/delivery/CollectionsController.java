package com.collections.delivery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;


public interface CollectionsController {
    ResponseEntity<Object> getAllCollectionsByFilter(String filter);
    ResponseEntity<Object> accessTokenCallback(String code);
}
