package com.collections.gateway.services.httpclient.impl;

import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.shared.exceptions.UnsplashUnauthorizedUserException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CollectionsHttpClientImpl implements CollectionsHttpClient {

    private CloseableHttpClient httpClient;

    private static final Logger logger = LoggerFactory.getLogger(CollectionsHttpClientImpl.class);

    @Override
    @SneakyThrows
    public String get(String uri) {
        String bodyResponse = "";
        logger.info("Performing request. URI: {}", uri);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);

            try (CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet)) {
                bodyResponse = handleResponse(closeableHttpResponse);
                logger.info("Success, building filtering response");
            }
        }
        return bodyResponse;
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
