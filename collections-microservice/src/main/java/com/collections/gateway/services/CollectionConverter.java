package com.collections.gateway.services;

import com.collections.gateway.dto.CollectionDto;
import com.collections.gateway.dto.CollectionsResponseDto;
import com.collections.shared.converter.GenericConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class CollectionConverter implements GenericConverter<String, CollectionsResponseDto> {

  // Clase que extiende del convertidor genérico, sobreescribe el método apply y convierte el DTO

  private final ObjectMapper mapper = new ObjectMapper();

  private static final Logger logger = LoggerFactory.getLogger(CollectionConverter.class);

  private CollectionsResponseDto getCollections(List<CollectionDto> collections) {
    return CollectionsResponseDto.builder()
            .data(collections)
            .build();
  }

  @Override
  public CollectionsResponseDto apply(String body) {
    List<CollectionDto> collectionsDto = null;
    try {
      logger.info("Mapping Unsplash response to CollectionsDTO");
      collectionsDto = mapper.readValue(body, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw new IllegalStateException();
    }
    return getCollections(collectionsDto);
  }
}
