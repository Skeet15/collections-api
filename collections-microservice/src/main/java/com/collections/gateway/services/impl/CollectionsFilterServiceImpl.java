package com.collections.gateway.services.impl;

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

    private CollectionsResponseDTO filterCollections(CollectionsResponseDTO collections, String filter) {
        CollectionsResponseDTO collectionsResponseDTO = CollectionsResponseDTO.builder()
                .collections(
                    collections.getCollections()
                            .stream()
                            .filter(collection -> collectionContainsFilter(collection, filter))
                            .collect(Collectors.toList())
                ).build();
        logger.info("Collections filtering result: {}", collectionsResponseDTO);
        return collectionsResponseDTO;
    }

    private boolean collectionContainsFilter(CollectionDTO collection, String filter) {
        return valueContainsFilter(collection.getId(), filter.toLowerCase()) ||
                valueContainsFilter(collection.getTitle(), filter.toLowerCase()) ||
                valueContainsFilter(collection.getDescription(), filter.toLowerCase()) ||
                valueContainsFilter(collection.getCoverPhotoId(), filter.toLowerCase());
    }

    private boolean valueContainsFilter(String value, String filter) {
        return Objects.nonNull(value) && value.toLowerCase().contains(filter.toLowerCase());
    }
}
