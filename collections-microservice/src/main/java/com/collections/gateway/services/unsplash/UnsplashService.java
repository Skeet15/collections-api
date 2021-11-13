package com.collections.gateway.services.unsplash;


import com.collections.gateway.dto.CollectionsResponseDto;
import com.collections.gateway.dto.UnsplashOauthTokenResponseDto;

public interface UnsplashService {

  CollectionsResponseDto getCollections();

  UnsplashOauthTokenResponseDto getAccessToken(String code);
}
