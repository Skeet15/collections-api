package com.collections.gateway.services.impl;

import com.collections.gateway.dto.CollectionDto;
import com.collections.gateway.dto.CollectionsResponseDto;
import com.collections.gateway.services.CollectionsFilterService;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class CollectionsFilterServiceImpl implements CollectionsFilterService {

  private static final Logger logger = LoggerFactory.getLogger(CollectionsFilterServiceImpl.class);

  @Override
  public CollectionsResponseDto filter(CollectionsResponseDto collections, String filter) {
    logger.info("Filtering collections: {}", collections);
    // Si no hay filtro se devuelve todas las colecciones
    // En otro caso se filtra por el parametro
    return
            Objects.isNull(filter) ? collections :
                    filterCollections(collections, filter);
  }

  private CollectionsResponseDto filterCollections(CollectionsResponseDto collections, String filter) {
    // Dado el DTO de colecciones llamamos al método que se encargar de filtrar las colecciones
    CollectionsResponseDto collectionsResponseDto = CollectionsResponseDto.builder()
            .data(
                    Optional.ofNullable(collections.getData())
                            .orElse(Collections.emptyList())
                            .stream()
                            .filter(collection -> collectionContainsFilter(collection, filter))
                            .collect(Collectors.toList())

            ).build();
    logger.info("Collections filtering result: {}", collectionsResponseDto);
    return collectionsResponseDto;
  }


  private boolean collectionContainsFilter(CollectionDto collection, String filter) {
    // Este método ejecute un OR lógico entre los campos por los que se quiere filtrar
    return valueContainsFilter(collection.getId(), filter.toLowerCase())
            || valueContainsFilter(collection.getTitle(), filter.toLowerCase())
            || valueContainsFilter(collection.getDescription(), filter.toLowerCase())
            || valueContainsFilter(collection.getCoverPhotoId(), filter.toLowerCase());
  }

  private boolean valueContainsFilter(String value, String filter) {
    return Objects.nonNull(value) && value.toLowerCase().contains(filter.toLowerCase());
  }
}
