package com.collections.delivery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;



public interface CollectionsController {
    ResponseEntity<Object> getAllCollectionsByFilter(@RequestParam(required = false) String filter);

    ResponseEntity<Object> getAllCollectionsByFilter(@RequestParam(required = false) String id,
                                                                  @RequestParam(required = false) String title,
                                                                  @RequestParam(required = false) String description,
                                                                  @RequestParam(required = false) String cover_photo_id);
}
