package com.collections.gateway.services;

import com.collections.gateway.dto.CollectionsResponseDTO;

public interface CollectionsConverter {
    CollectionsResponseDTO toCollectionsResponseDto(String body);
}
