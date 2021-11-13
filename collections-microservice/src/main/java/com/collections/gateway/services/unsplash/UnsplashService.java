package com.collections.gateway.services.unsplash;


import com.collections.gateway.dto.CollectionsResponseDTO;
import com.collections.gateway.dto.UnsplashOAuthTokenResponseDto;

public interface UnsplashService {

    CollectionsResponseDTO getCollections();
    UnsplashOAuthTokenResponseDto getAccessToken(String code);
}
