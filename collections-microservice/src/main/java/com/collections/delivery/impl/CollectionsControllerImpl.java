package com.collections.delivery.impl;

import com.collections.delivery.CollectionsController;
import com.collections.gateway.response.ResponseHandler;
import com.collections.gateway.services.CollectionsConverter;
import com.collections.gateway.services.CollectionsFilterService;
import com.collections.gateway.services.unsplash.UnsplashService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionsControllerImpl implements CollectionsController {

    private static final Logger logger = LoggerFactory.getLogger(CollectionsControllerImpl.class);

    private final UnsplashService unsplashService;

    private final CollectionsConverter collectionsConverter;

    private final CollectionsFilterService collectionsFilterService;

    @Override
    @GetMapping("all")
    public ResponseEntity<Object> getAllCollectionsByFilter(@RequestParam(required = false) String filter) {
        logger.info("Get collections by filter {}", filter);
        return ResponseHandler.generateResponse("Success", collectionsFilterService
                .filter(collectionsConverter.toCollectionsResponseDto(unsplashService.getCollections()), filter),
                HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Object> getAllCollectionsByFilter(@RequestParam(required = false) String id,
                                                                         @RequestParam(required = false) String title,
                                                                         @RequestParam(required = false) String description,
                                                                         @RequestParam(required = false) String cover_photo_id) {
        logger.info("Get collections by id {}, title {}, description {}, cover_photo_id {}", id, title, description, cover_photo_id);
        return ResponseHandler.generateResponse("Success", collectionsFilterService
                .filter(collectionsConverter.toCollectionsResponseDto(unsplashService.getCollections()), id, title, description, cover_photo_id)
                , HttpStatus.OK);
    }

}
