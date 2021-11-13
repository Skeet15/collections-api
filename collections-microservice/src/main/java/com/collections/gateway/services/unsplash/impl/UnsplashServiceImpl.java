package com.collections.gateway.services.unsplash.impl;

import com.collections.configuration.UnsplashConfiguration;
import com.collections.gateway.dto.CollectionsResponseDto;
import com.collections.gateway.dto.UnsplashOauthTokenResponseDto;
import com.collections.gateway.services.AccessTokenConverter;
import com.collections.gateway.services.CollectionConverter;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.gateway.services.unsplash.UnsplashService;
import java.util.HashMap;
import java.util.Map;
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
  public CollectionsResponseDto getCollections() {
    // Dado el access token y la llamada a /collections a UnsplashAPI, devuelve las colecciones
    logger.info("Request to Unsplash API, using HttpClient...");
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + unsplashConfiguration.getAccessToken());
    return collectionsConverter.apply(collectionsHttpClient.get(createGetCollectionsUri(), headers));
  }

  private String createGetCollectionsUri() {
    // Creamos la URI para llamar a collections de unsplash
    logger.info("Creating Get Collections Unsplash API URI");
    String uri = createUri(unsplashConfiguration.getHost(), unsplashConfiguration.getPath(), new HashMap<>());
    logger.info("Request URI: {}", uri);
    return uri;
  }

  @Override
  public UnsplashOauthTokenResponseDto getAccessToken(String code) {
    // Se obtiene el access token despues de que se llame al callback con el authorization_code
    logger.info("Obtaining access token...");
    UnsplashOauthTokenResponseDto unsplashOauthTokenResponseDto = accessTokenConverter.apply(collectionsHttpClient.post(createAccessTokenUri(code), new HashMap<>()));
    unsplashConfiguration.setAccessToken(unsplashOauthTokenResponseDto.getAccessToken());
    logger.info("Everything OK access token: " + unsplashOauthTokenResponseDto.getAccessToken());
    return unsplashOauthTokenResponseDto;
  }

  private String createAccessTokenUri(String code) {
    //Creación de la URI para obtener el access token
    logger.info("Creating Post Access Token Unsplash API URI");
    Map<String, String> parameters = new HashMap<>();
    parameters.put("client_id", unsplashConfiguration.getClientId());
    parameters.put("client_secret", unsplashConfiguration.getOauthClientSecret());
    parameters.put("redirect_uri", unsplashConfiguration.getOauthRedirectUri());
    parameters.put("code", code);
    parameters.put("grant_type", unsplashConfiguration.getOauthAuthorizationGrantType());
    String uri = createUri(unsplashConfiguration.getOauthAccessTokenUri(), "", parameters);
    logger.info("Request URI: {}", uri);
    return uri;
  }

  private String createUri(String host, String path, Map<String, String> parameters) {
    // Método común para la creación de URIs
    logger.info("Creating URI");
    URIBuilder uriBuilder;
    String uri = "";
    uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost(host)
            .setPath(path);
    parameters.forEach(uriBuilder::setParameter);
    uri = uriBuilder.toString();
    logger.info("Request URI: {}", uri);
    return uri;
  }


}
