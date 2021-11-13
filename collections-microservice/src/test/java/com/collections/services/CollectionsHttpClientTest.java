package com.collections.services;

import com.collections.configuration.UnsplashConfiguration;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.gateway.services.httpclient.impl.CollectionsHttpClientImpl;
import com.collections.shared.exceptions.UnauthorizedUserException;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpClientBuilder.class, HttpClients.class, EntityUtils.class})
@PowerMockIgnore({"javax.net.ssl.*"})
public class CollectionsHttpClientTest {


  private final CollectionsHttpClient collectionsHttpClient = new CollectionsHttpClientImpl(Mockito.mock(UnsplashConfiguration.class));

  private final CloseableHttpResponse closeableHttpResponse = Mockito.mock(CloseableHttpResponse.class);


  @Test
  public void callToGet_thenGetBodyResponse() throws Exception {
    URIBuilder uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost("host");

    CloseableHttpClient closeableHttpClient = PowerMockito.mock(CloseableHttpClient.class);
    CloseableHttpResponse closeableHttpResponse = PowerMockito.mock(CloseableHttpResponse.class);


    StatusLine statusLine = PowerMockito.mock(StatusLine.class);
    PowerMockito.when(statusLine.getStatusCode()).thenReturn(200);
    PowerMockito.when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);


    PowerMockito.mockStatic(HttpClients.class);
    PowerMockito.mockStatic(EntityUtils.class);

    HttpEntity httpEntity = PowerMockito.mock(HttpEntity.class);
    PowerMockito.when(EntityUtils.toString(httpEntity)).thenReturn("[]");
    PowerMockito.when(HttpClients.class, "createDefault").thenReturn(closeableHttpClient);
    PowerMockito.when(closeableHttpClient.execute(Mockito.any())).thenReturn(closeableHttpResponse);


    collectionsHttpClient.get(uriBuilder.toString(), new HashMap<>());
  }


  @Test(expected = UnauthorizedUserException.class)
  public void callToGet_thenGetBodyResponse_throwUnauthorized() throws Exception {
    URIBuilder uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost("host");

    CloseableHttpClient closeableHttpClient = PowerMockito.mock(CloseableHttpClient.class);
    CloseableHttpResponse closeableHttpResponse = PowerMockito.mock(CloseableHttpResponse.class);


    StatusLine statusLine = PowerMockito.mock(StatusLine.class);
    PowerMockito.when(statusLine.getStatusCode()).thenReturn(401);
    PowerMockito.when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);


    PowerMockito.mockStatic(HttpClients.class);

    PowerMockito.when(HttpClients.class, "createDefault").thenReturn(closeableHttpClient);
    PowerMockito.when(closeableHttpClient.execute(Mockito.any())).thenReturn(closeableHttpResponse);


    collectionsHttpClient.get(uriBuilder.toString(), new HashMap<>());
  }


  @Test(expected = IllegalStateException.class)
  public void callToGet_thenGetBodyResponse_throwInternalServerError() throws Exception {
    URIBuilder uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost("host");

    CloseableHttpClient closeableHttpClient = PowerMockito.mock(CloseableHttpClient.class);
    CloseableHttpResponse closeableHttpResponse = PowerMockito.mock(CloseableHttpResponse.class);


    StatusLine statusLine = PowerMockito.mock(StatusLine.class);
    PowerMockito.when(statusLine.getStatusCode()).thenReturn(500);
    PowerMockito.when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);


    PowerMockito.mockStatic(HttpClients.class);

    PowerMockito.when(HttpClients.class, "createDefault").thenReturn(closeableHttpClient);
    PowerMockito.when(closeableHttpClient.execute(Mockito.any())).thenReturn(closeableHttpResponse);


    collectionsHttpClient.get(uriBuilder.toString(), new HashMap<>());
  }


}

