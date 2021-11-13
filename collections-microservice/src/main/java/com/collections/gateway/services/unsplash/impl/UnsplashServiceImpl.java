package com.collections.gateway.services.unsplash.impl;

import com.collections.configuration.UnsplashConfiguration;
import com.collections.gateway.dto.CollectionsResponseDTO;
import com.collections.gateway.dto.UnsplashOAuthTokenResponseDto;
import com.collections.gateway.services.AccessTokenConverter;
import com.collections.gateway.services.CollectionConverter;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.gateway.services.unsplash.UnsplashService;
import lombok.AllArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UnsplashServiceImpl implements UnsplashService {

    private final CollectionsHttpClient collectionsHttpClient;

    private final UnsplashConfiguration unsplashConfiguration;

    private final CollectionConverter collectionsConverter;

    private final AccessTokenConverter accessTokenConverter;

    private static final Logger logger = LoggerFactory.getLogger(UnsplashServiceImpl.class);

    @Override
    public CollectionsResponseDTO getCollections() {
        logger.info("Request to Unsplash API, using HttpClient...");
        return collectionsConverter.apply(collectionsHttpClient.get(createGetCollectionsUri()));
    }

    // TODO codigo duplicado
    private String createGetCollectionsUri() {
        logger.info("Creating Get Collections Unsplash API URI");
        URIBuilder uriBuilder = null;
        String uri = "";
        uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost(unsplashConfiguration.getHost())
                .setPath(unsplashConfiguration.getPath());
        uri = uriBuilder.toString();
        logger.info("Request URI: {}", uri);
        return uri;
    }

    private String createAccessTokenUri(String code) {
        logger.info("Creating Post Access Token Unsplash API URI");
        URIBuilder uriBuilder = null;
        String uri = "";
        uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost(unsplashConfiguration.getOauthAccessTokenUri());

        uriBuilder.addParameter("client_id", unsplashConfiguration.getClientId());
        uriBuilder.addParameter("client_secret", unsplashConfiguration.getOauthClientSecret());
        uriBuilder.addParameter("redirect_uri", unsplashConfiguration.getOauthRedirectUri());
        uriBuilder.addParameter("code", code);
        uriBuilder.addParameter("grant_type", unsplashConfiguration.getOauthAuthorizationGrantType());
        uri = uriBuilder.toString();
        logger.info("Request URI: {}", uri);
        return uri;
    }

    @Override
    public UnsplashOAuthTokenResponseDto getAccessToken(String code) {
        UnsplashOAuthTokenResponseDto unsplashOAuthTokenResponseDto = accessTokenConverter.apply(collectionsHttpClient.post(createAccessTokenUri(code)));
        unsplashConfiguration.setAccessToken(unsplashOAuthTokenResponseDto.getAccessToken());
        return unsplashOAuthTokenResponseDto;
    }

}
