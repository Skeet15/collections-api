package com.collections.gateway.services;

import com.collections.gateway.dto.CollectionsResponseDto;


public interface CollectionsFilterService {

  CollectionsResponseDto filter(CollectionsResponseDto collections, String filter);
}
