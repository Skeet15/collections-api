package com.collections.gateway.services.unsplash.impl;

import com.collections.configuration.UnsplashConfiguration;
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

    private static final Logger logger = LoggerFactory.getLogger(UnsplashServiceImpl.class);

    @Override
    public String getCollections() {
        logger.info("Request to Unsplash API, using HttpClient...");
        return collectionsHttpClient.get(createUri());
    }

    private String createUri() {
        logger.info("Creating Unsplash API URI");
        URIBuilder uriBuilder = null;
        String uri = "";
        uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost(unsplashConfiguration.getHost())
                .setPath(unsplashConfiguration.getPath());
        uriBuilder.addParameter("client_id", unsplashConfiguration.getClientId());
        uri = uriBuilder.toString();
        logger.info("Request URI: {}", uri);
        return uri;
    }

}
