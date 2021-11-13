package com.collections.gateway.services.httpclient.impl;

import com.collections.configuration.UnsplashConfiguration;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.shared.exceptions.BadRequestException;
import com.collections.shared.exceptions.UnauthorizedUserException;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
  public String get(@NonNull String uri, @NonNull Map<String, String> headers) {
    // Método que crea la llamada GET haciendo uso del cliente apache
    logger.info("Performing request. URI: {}", uri);
    HttpGet httpGet = createHttpGetRequest(uri, headers);
    // Para después ejecutar el get devolviendo la respuesta
    return executeGet(httpGet);
  }

  private HttpGet createHttpGetRequest(String uri, Map<String, String> headers) {
    //Este método añade las cabeceras pasadas por parámetro y devuelve el objeto HttpGet
    HttpGet httpGet = new HttpGet(uri);
    headers.forEach(httpGet::addHeader);
    return httpGet;
  }

  @Override
  public String post(@NonNull String uri, @NonNull Map<String, String> headers) {
    // Método que crea la llamada POST haciendo uso del cliente apache
    logger.info("Performing request. URI: {}", uri);
    HttpPost httpPost = createHttpPostRequest(uri, headers);
    // Para después ejecutar el post devolviendo la respuesta
    return executePost(httpPost);
  }

  private HttpPost createHttpPostRequest(String uri, Map<String, String> headers) {
    // Este método añade las cabeceras pasadas por parámetro y devuelve el objeto HttpPost
    HttpPost httpPost = new HttpPost(uri);
    headers.forEach(httpPost::addHeader);
    return httpPost;
  }


  @SneakyThrows
  private String executeGet(HttpGet httpGet) {
    // Método que ejecuta la llamada get
    String bodyResponse = "";
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
    // Método que ejecuta la llamada post
    String bodyResponse = "";
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      try (CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost)) {
        bodyResponse = handleResponse(closeableHttpResponse);
        logger.info("Success, building response");
      }
    }
    return bodyResponse;
  }

  @SneakyThrows
  protected String handleResponse(@NonNull CloseableHttpResponse closeableHttpResponse) {
    // Este método se encarga de tratar la respuesta y lanzar excepcion si no es lo esperado
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
        throw new UnauthorizedUserException();
      case HttpStatus.SC_INTERNAL_SERVER_ERROR:
      case HttpStatus.SC_SERVICE_UNAVAILABLE:
        logger.info("Response status code: {}", statusLine.getStatusCode());
        throw new IllegalStateException();
      case HttpStatus.SC_BAD_REQUEST:
        logger.info("Response status code: {}", statusLine.getStatusCode());
        throw new BadRequestException();
      default:
        logger.info("Response status code: {}", statusLine.getStatusCode());
        throw new RuntimeException();
    }
    logger.info("Consuming response entity");
    EntityUtils.consume(entity);
    return bodyResponse;
  }
}
