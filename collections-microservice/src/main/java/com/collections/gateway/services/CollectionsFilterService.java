package com.collections.gateway.services;


import com.collections.gateway.dto.CollectionDTO;
import com.collections.gateway.dto.CollectionsResponseDTO;

import java.util.List;

public interface CollectionsFilterService {

    CollectionsResponseDTO filter(CollectionsResponseDTO collections, String filter);

    CollectionsResponseDTO filter(CollectionsResponseDTO collections, String id, String title, String description, String cover_photo_id);
}
