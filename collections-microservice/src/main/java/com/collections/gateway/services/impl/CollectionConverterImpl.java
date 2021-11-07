package com.collections.gateway.services.impl;

import com.collections.delivery.impl.CollectionsControllerImpl;
import com.collections.gateway.dto.CollectionDTO;
import com.collections.gateway.dto.CollectionsResponseDTO;
import com.collections.gateway.services.CollectionsConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionConverterImpl implements CollectionsConverter {

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(CollectionsControllerImpl.class);

    private CollectionsResponseDTO getCollections (List<CollectionDTO> collections) {
        return CollectionsResponseDTO.builder()
                .collections(collections)
                .build();
    }

    @Override
    public CollectionsResponseDTO toCollectionsResponseDto(String body) {
        List<CollectionDTO> collectionsDTO = null;
        try {
            logger.info("Mapping Unsplash response to CollectionsDTO");
            collectionsDTO = mapper.readValue(body, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException();
        }
        return getCollections(collectionsDTO);
    }
}
