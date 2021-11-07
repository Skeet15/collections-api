package com.collections.gateway.services.impl;

import com.collections.delivery.impl.CollectionsControllerImpl;
import com.collections.gateway.dto.CollectionDTO;
import com.collections.gateway.dto.CollectionsResponseDTO;
import com.collections.gateway.services.CollectionsFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CollectionsFilterServiceImpl implements CollectionsFilterService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionsFilterServiceImpl.class);

    @Override
    public CollectionsResponseDTO filter(CollectionsResponseDTO collections, String filter) {
        logger.info("Filtering collections: {}", collections);
        return
                Objects.isNull(filter) ? collections :
                        filterCollections(collections, filter);
    }

    @Override
    public CollectionsResponseDTO filter(CollectionsResponseDTO collections, String id, String title, String description, String cover_photo_id) {
        return CollectionsResponseDTO.builder()
                .collections(
                        collections.getCollections()
                                .stream()
                                .filter(collection -> containsFilter(collection.getId(), id))
                                .filter(collection -> containsFilter(collection.getTitle(), title))
                                .filter(collection -> containsFilter(collection.getDescription(), description))
                                .filter(collection -> containsFilter(collection.getCoverPhotoId(), cover_photo_id))
                                .collect(Collectors.toList())
                ).build();
    }

    private boolean containsFilter(String value, String filter) {
        if (Objects.isNull(filter)) {
            return true;
        }

        if (filter.isEmpty()) {
            return false;
        }
        return value.toLowerCase().contains(filter.toLowerCase());

    }

    private CollectionsResponseDTO filterCollections(CollectionsResponseDTO collections, String filter) {
        CollectionsResponseDTO collectionsResponseDTO = CollectionsResponseDTO.builder()
                .collections(
                    collections.getCollections()
                            .stream()
                            .filter(collection -> containsFilter(collection, filter))
                            .collect(Collectors.toList())
                ).build();
        logger.info("Collections filtering result: {}", collectionsResponseDTO);
        return collectionsResponseDTO;
    }

    private boolean containsFilter(CollectionDTO collection, String filter) {
        boolean match = false;
        if (!filter.isEmpty()) {
            String[] filterArray = filter.split(" ");
            String field = filterArray[0];
            String value = filterArray[1];
            String fieldToMatch = "";
            switch (field) {
                case "id": fieldToMatch = collection.getId(); break;
                case "title": fieldToMatch = collection.getTitle(); break;
                case "description": fieldToMatch = collection.getDescription(); break;
                case "cover_photo_id": fieldToMatch = collection.getCoverPhotoId(); break;
            }
            match = fieldToMatch.toLowerCase().contains(value.toLowerCase());
        }
        return match;
    }
}
