package com.collections.gateway.services;

import com.collections.gateway.dto.CollectionsResponseDTO;


public interface CollectionsFilterService {

    CollectionsResponseDTO filter(CollectionsResponseDTO collections, String filter);
}
