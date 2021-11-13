package com.collections.gateway.services.httpclient.impl;

import com.collections.configuration.UnsplashConfiguration;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.shared.exceptions.UnsplashUnauthorizedUserException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CollectionsHttpClientImpl implements CollectionsHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(CollectionsHttpClientImpl.class);

    private final UnsplashConfiguration unsplashConfiguration;

    @Override
    @SneakyThrows
    public String get(String uri) {
        logger.info("Performing request. URI: {}", uri);
        HttpGet httpGet = createHttpGetRequest(uri);
        return executeGet(httpGet);
    }

    @Override
    public String post(String uri) {
        HttpPost httpPost = createHttpPostRequest(uri);
        return executePost(httpPost);
    }

    private HttpPost createHttpPostRequest(String uri) {
        return new HttpPost(uri);
    }

    @SneakyThrows
    private String executeGet(HttpGet httpGet) {
        String bodyResponse = "";
        httpGet.setHeader("Authorization", "Bearer " + unsplashConfiguration.getAccessToken());
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet)) {
                bodyResponse = handleResponse(closeableHttpResponse);
                logger.info("Success, building filtering response");
            }
        }
        return bodyResponse;
    }

    @SneakyThrows
    private String executePost(HttpPost httpPost) {
        String bodyResponse = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost)) {
                bodyResponse = handleResponse(closeableHttpResponse);
                logger.info("Success, building response");
            }
        }
        return bodyResponse;
    }



    private HttpGet createHttpGetRequest(String uri) {
        return new HttpGet(uri);
    }

    @SneakyThrows
    protected String handleResponse(CloseableHttpResponse closeableHttpResponse) {
        StatusLine statusLine = closeableHttpResponse.getStatusLine();
        HttpEntity entity = closeableHttpResponse.getEntity();
        String bodyResponse = "";
        logger.info("Handling response");
        switch (statusLine.getStatusCode()) {
            case HttpStatus.SC_OK:
                logger.info("Response status code: {}", statusLine.getStatusCode());
                bodyResponse = EntityUtils.toString(entity);
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                logger.info("Response status code: {}", statusLine.getStatusCode());
                throw new UnsplashUnauthorizedUserException();
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
            case HttpStatus.SC_SERVICE_UNAVAILABLE:
                logger.info("Response status code: {}", statusLine.getStatusCode());
                throw new IllegalStateException();
        }
        logger.info("Consuming response entity");
        EntityUtils.consume(entity);
        return bodyResponse;
    }
}
